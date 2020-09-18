package com.ks.plugin.dialog;

import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiPackage;
import com.ks.plugin.InflateClass;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.event.*;

public class KsFormatDialog extends JDialog {

	private JPanel				contentPane;

	private JButton				buttonOK;																	// 确定

	private JButton				buttonCancel;																// 取消

	private JTextField			viewNameField;																// View 命名

	private JCheckBox			layoutCheckBox;																// layout CheckBox

	private JTextField			layoutNameField;															// layout 命名

	private JComboBox<String>	sourceLanguageCBox;															// 变成语言选择

	private JCheckBox			viewModelCheckBox;															// viewModel CheckBox

	private JTextField			viewModelField;																// viewModel 命名

	private JComboBox<String>	extendsBaseCB;																// 继承类选择

	private JComboBox<String>	viewModelPackageNameCB;														// ViewModel 包名

	private JComboBox<String>	repositoryPackageNameCB;													// Repository 包名

	private JTextField			repositoryClassNameField;													// Repository 命名

	private JTextField			serviceClassNameField;														// Service 命名

	// 继承的类集合
	private final String[]		expandsViews				= new String[] { "AbsActivity", "AbsFragment" };

	// 语言集合
	private final String[]		sourceLanguageTexts			= new String[] { "Kotlin", "Java" };

	// 创建类名
	private String				createClassName				= "KsActivity";

	// 创建ViewModelName
	private String				createViewModelName			= "KsViewModel";

	// 创建Repository
	private String				createRepositoryName		= "KsRepository";

	// 创建Service
	private String				createServiceName			= "KsService";

	// 创建LayoutName
	private String				createLayoutName			= "ks_layout";

	// 选择继承的类
	private String				selectExpandClassName		= "AbsActivity";

	// 选择的开发语言
	private String				selectSourceLanguageName	= "Kotlin";

	private AnActionEvent		event;

	public KsFormatDialog(AnActionEvent event) {
		this.event = event;
		setContentPane(contentPane);
		setModal(true);
		getRootPane().setDefaultButton(buttonOK);

		// OK
		buttonOK.addActionListener(e -> onOK());

		// 取消
		buttonCancel.addActionListener(e -> onCancel());

		// call onCancel() when cross is clicked
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				onCancel();
			}
		});

		// call onCancel() on ESCAPE
		contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

		// 继承类选择事件
		extendsBaseCB.addItemListener(itemEvent -> {
			selectExpandClassName = (String) itemEvent.getItem();
			if (viewNameField.getText().equals("KsFragment") && selectExpandClassName.equals(expandsViews[0])) { // AbsActivity
				viewNameField.setText("KsActivity");
				return;
			}
			if (viewNameField.getText().equals("KsActivity") && selectExpandClassName.equals(expandsViews[1])) { // AbsFragment
				viewNameField.setText("KsFragment");
			}
		});

		// 开发语言选择事件
		sourceLanguageCBox.addItemListener(itemEvent -> selectSourceLanguageName = (String) itemEvent.getItem());

		// viewModel checkbox监听
		viewModelCheckBox.addActionListener(e -> {
			if (isOkEnable()) {
				buttonOK.setEnabled(true);
			} else {
				buttonOK.setEnabled(false);
			}
		});

		// layout name checkbox监听
		layoutCheckBox.addActionListener(e -> {
			if (isOkEnable()) {
				buttonOK.setEnabled(true);
			} else {
				buttonOK.setEnabled(false);
			}
		});

		// 类输入框监听输入
		viewNameField.getDocument().addDocumentListener(new DocumentListener() {

			@Override public void insertUpdate(DocumentEvent e) {
				if (isOkEnable()) buttonOK.setEnabled(true);
			}

			@Override public void removeUpdate(DocumentEvent e) {
				Document document = e.getDocument();
				try {
					String content = document.getText(0, document.getLength());
					if ((content == null || "".equals(content))) {
						buttonOK.setEnabled(false);
					}
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			}

			@Override public void changedUpdate(DocumentEvent e) {}
		});

		// viewModel输入框监听输入
		viewModelField.getDocument().addDocumentListener(new DocumentListener() {

			@Override public void insertUpdate(DocumentEvent e) {
				if (!viewModelCheckBox.isSelected()) return;
				if (isOkEnable()) buttonOK.setEnabled(true);
			}

			@Override public void removeUpdate(DocumentEvent e) {
				if (!viewModelCheckBox.isSelected()) return;
				Document document = e.getDocument();
				try {
					String content = document.getText(0, document.getLength());
					if ((content == null || "".equals(content))) {
						buttonOK.setEnabled(false);
					}
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			}

			@Override public void changedUpdate(DocumentEvent e) {}
		});

		// Layout输入框监听输入
		layoutNameField.getDocument().addDocumentListener(new DocumentListener() {

			@Override public void insertUpdate(DocumentEvent e) {
				if (!layoutCheckBox.isSelected()) return;
				if (isOkEnable()) buttonOK.setEnabled(true);
			}

			@Override public void removeUpdate(DocumentEvent e) {
				if (!layoutCheckBox.isSelected()) return;
				Document document = e.getDocument();
				try {
					String content = document.getText(0, document.getLength());
					if ((content == null || "".equals(content))) {
						buttonOK.setEnabled(false);
					}
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			}

			@Override public void changedUpdate(DocumentEvent e) {}
		});

		initData();
	}

	private boolean isOkEnable() {
		return viewNameField.getText().length() > 0 && (!viewModelCheckBox.isSelected() || viewModelField.getText().length() > 0)
				&& (!layoutCheckBox.isSelected() || layoutNameField.getText().length() > 0);
	}

	// 初始化
	private void initData() {
		viewNameField.setText(createClassName);
		viewModelField.setText(createViewModelName);
		layoutNameField.setText(createLayoutName);
		repositoryClassNameField.setText(createRepositoryName);
		serviceClassNameField.setText(createServiceName);

		// 添加继承Base数据
		addExpandsBaseData();

		// 添加语言数据
		addSourceLanguageData();

		// 添加ViewModel和Repository包名数据
		addViewModelAndRepositoryPackageNameData();

	}

	// 添加继承选择数据
	private void addExpandsBaseData() {
		extendsBaseCB.removeAllItems();
		for (String view : expandsViews) {
			extendsBaseCB.addItem(view);
		}
	}

	// 添加语言选择数据
	private void addSourceLanguageData() {
		sourceLanguageCBox.removeAllItems();
		for (String view : sourceLanguageTexts) {
			sourceLanguageCBox.addItem(view);
		}
	}

	// 添加viewModel和Repository包名
	private void addViewModelAndRepositoryPackageNameData() {
		// 获取鼠标选中的目录
		IdeView ideView = event.getRequiredData(LangDataKeys.IDE_VIEW);
		PsiDirectory[] directorys = ideView.getDirectories();
		String[] viewModelPackageName = new String[directorys.length];
		for (int i = 0; i < directorys.length; i++) {
			PsiDirectory directory = directorys[i];
			assert directory != null;
			PsiPackage psiPackage = JavaDirectoryService.getInstance().getPackage(directory);
			viewModelPackageName[i] = psiPackage != null ? psiPackage.getQualifiedName() : "";
		}
		viewModelPackageNameCB.removeAllItems();
		repositoryPackageNameCB.removeAllItems();
		for (String view : viewModelPackageName) {
			viewModelPackageNameCB.addItem(view);
			repositoryPackageNameCB.addItem(view);
		}

		viewModelPackageNameCB.setEditable(true);
		repositoryPackageNameCB.setEditable(true);
	}

	private void onOK() {
		// add your code here
		if (viewNameField == null || viewNameField.getText() == null || "".equals(viewNameField.getText())) {
			JOptionPane.showMessageDialog(null, "请填写要创建的类名");
			return;
		}
		if (viewModelCheckBox.isSelected() && (viewModelField == null || viewModelField.getText() == null || "".equals(viewModelField.getText()))) {
			JOptionPane.showMessageDialog(null, "请填写要创建的ViewModel名");
			return;
		}
		if (layoutCheckBox.isSelected() && (layoutNameField == null || layoutNameField.getText() == null || "".equals(layoutNameField.getText()))) {
			JOptionPane.showMessageDialog(null, "请填写要创建的Layout名");
			return;
		}

		// 加载类
		new InflateClass(event).writeAction(expandsViews[0].equals(selectExpandClassName) ? 1 : 2, viewNameField.getText(), viewModelCheckBox.isSelected(), viewModelField.getText(),
				layoutCheckBox.isSelected(), layoutNameField.getText(), viewModelPackageNameCB.getSelectedItem() != null ? viewModelPackageNameCB.getSelectedItem().toString() : "",
				repositoryPackageNameCB.getSelectedItem() != null ? repositoryPackageNameCB.getSelectedItem().toString() : "", repositoryClassNameField.getText(), serviceClassNameField.getText(),
				sourceLanguageTexts[0].equals(selectSourceLanguageName));

		dispose();
	}

	private void onCancel() {
		// add your code here if necessary
		dispose();
	}

	public static void showKsFormatDialog(AnActionEvent event) {
		KsFormatDialog dialog = new KsFormatDialog(event);
		// dialog.setSize(340, 220);
		dialog.setTitle("New KsFrame Format");
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}
