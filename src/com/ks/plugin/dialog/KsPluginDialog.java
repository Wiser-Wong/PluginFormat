package com.ks.plugin.dialog;

import com.intellij.openapi.actionSystem.AnActionEvent;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Wiser
 *
 * 插件弹窗
 */
public class KsPluginDialog extends JDialog {

	private JPanel contentPane;

	private JButton buttonOK;

	private JButton buttonCancel;

	private JTextField textClassName;

	private JRadioButton radioButtonView;

	private JRadioButton radioButtonAdapter;

	private JRadioButton radioButtonService;

	private JComboBox<String> comboBoxExpand;

	private JCheckBox checkBoxIsBiz;

	private JRadioButton radioButtonJava;

	private JRadioButton radioButtonKotlin;

	private String selectExpandClassName = "KsBaseActivity";

	private int type = -1;

	private final String[] views = new String[]{"KsBaseActivity", "KsBaseFragment"};

	private final String[] adapters = new String[]{"KsRVAdapter"};

	private final String[] services = new String[]{"KsService"};

	private AnActionEvent event;

	public KsPluginDialog(AnActionEvent event) {
		this.event = event;
		setContentPane(contentPane);
		setModal(true);
		getRootPane().setDefaultButton(buttonOK);

		// 单选框View和Adapter和Service
		ButtonGroup groupType = new ButtonGroup();
		radioButtonView.setSelected(true);
		groupType.add(radioButtonView);
		groupType.add(radioButtonAdapter);
		groupType.add(radioButtonService);

		// 单选框Java和Kotlin
		ButtonGroup groupLanguage = new ButtonGroup();
		radioButtonJava.setSelected(true);
		groupLanguage.add(radioButtonJava);
		groupLanguage.add(radioButtonKotlin);

		// 默认下拉窗View
		switchClassType(0);

		radioButtonView.addActionListener(actionEvent -> switchClassType(0));

		radioButtonAdapter.addActionListener(actionEvent -> switchClassType(1));

		radioButtonService.addActionListener(actionEvent -> switchClassType(2));

		checkBoxIsBiz.addActionListener(actionEvent -> {
		});

		comboBoxExpand.addItemListener(itemEvent -> selectExpandClassName = (String) itemEvent.getItem());

		buttonOK.addActionListener(e -> onOK());

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

	}

	// 切换单选按钮下拉窗替换数据
	private void switchClassType(int type) {
		if (this.type == type) return;
		this.type = type;
		comboBoxExpand.removeAllItems();
		switch (type) {
			case 0:// view
				checkBoxIsBiz.setVisible(true);
				for (String view : views) {
					comboBoxExpand.addItem(view);
				}
				break;
			case 1:// adapter
				checkBoxIsBiz.setVisible(false);
				for (String view : adapters) {
					comboBoxExpand.addItem(view);
				}
				break;
			case 2:// service
				checkBoxIsBiz.setVisible(true);
				for (String view : services) {
					comboBoxExpand.addItem(view);
				}
				break;
		}
	}

	// 确定
	private void onOK() {
		if (textClassName == null || textClassName.getText() == null || "".equals(textClassName.getText())) {
			JOptionPane.showMessageDialog(null, "请填写要创建的类名");
			return;
		}
		// Enumeration<AbstractButton> radioButtons = group.getElements();
		// while (radioButtons.hasMoreElements()) {
		// JRadioButton jr = (JRadioButton) radioButtons.nextElement();
		// if (jr.isSelected()) {
		// JOptionPane.showMessageDialog(null, "填写的类名是：" + textClassName.getText() +
		// "选择的类型是:" + jr.getText() + "选择继承类是：" + selectExpandClassName);
		// }
		// }

//		new InflateClass(event).writeAction(textClassName.getText(),
//				(selectExpandClassName.equals(views[0]) ? 1
//						: selectExpandClassName.equals(views[1]) ? 2
//						: selectExpandClassName.equals(views[2]) ? 3 : selectExpandClassName.equals(adapters[0]) ? 4 : selectExpandClassName.equals(services[0]) ? 5 : 1),
//				checkBoxIsBiz.isSelected(), radioButtonJava.isSelected());

		dispose();

	}

	// 取消
	private void onCancel() {
		dispose();
	}

	public static void showPluginDialog(AnActionEvent event) {
		KsPluginDialog dialog = new KsPluginDialog(event);
		// dialog.setSize(340, 220);
		dialog.setTitle("New KsFrame Format");
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

}
