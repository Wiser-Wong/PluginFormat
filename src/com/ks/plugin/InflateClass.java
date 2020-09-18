package com.ks.plugin;

import com.intellij.ide.IdeView;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Wiser
 * 
 *         创建类工具
 */
public class InflateClass {

	private Project			project;

	private AnActionEvent	event;

	public InflateClass(AnActionEvent event) {
		this.event = event;
		this.project = event.getProject();
	}

	// 根据代码创建类
	private void createClassFromCode(int type, String className, boolean isCreateViewModel, String viewModelClassName, boolean isCreateLayout, String layoutName, String viewModelPackage,
			String repositoryPackage, String repositoryClassName, String serviceClassName, boolean isKotlin) throws Exception {
		// 获取鼠标选中的目录
		IdeView ideView = event.getRequiredData(LangDataKeys.IDE_VIEW);
		PsiDirectory directory = ideView.getOrChooseDirectory();
		assert directory != null;
		PsiPackage psiPackage = JavaDirectoryService.getInstance().getPackage(directory);

		if (isCreateViewModel)
			// 创建ViewModel
			writerFile(directory,viewModelPackage, viewModelClassName, repositoryPackage, repositoryClassName, serviceClassName, isKotlin);

		if (isKotlin)
			// 创建Kotlin类
			createKotlinClassFromCode(psiPackage, directory, type, className, isCreateViewModel, viewModelPackage,viewModelClassName, isCreateLayout, layoutName);
		else
			// 创建Java类
			createJavaClassFromCode(psiPackage, directory, type, className, isCreateViewModel, viewModelPackage,viewModelClassName, isCreateLayout, layoutName);

		if (isCreateLayout)
			// 同时创建xml布局
			createXmlFromCode(layoutName);

	}

	// 根据代码创建Kotlin类
	private void createKotlinClassFromCode(PsiPackage psiPackage, PsiDirectory directory, int type, String className, boolean isCreateViewModel,String viewModelPackage, String viewModelClassName, boolean isCreateLayout,
			String layoutName) throws Exception {
		// 没有就创建一个，第一次使用代码字符串创建个类
		PsiFile javaFile = PsiFileFactory.getInstance(project).createFileFromText(className + ".kt", JavaFileType.INSTANCE,
				(type == 1 ? CodeTool.KsActivityKotlinCode(psiPackage.getQualifiedName(), className, isCreateViewModel,viewModelPackage, viewModelClassName, isCreateLayout, layoutName)
						: type == 2 ? CodeTool.KsFragmentKotlinCode(psiPackage.getQualifiedName(), className, isCreateViewModel, viewModelPackage,viewModelClassName, isCreateLayout, layoutName)
								: CodeTool.KsActivityKotlinCode(psiPackage.getQualifiedName(), className, isCreateViewModel,viewModelPackage, viewModelClassName, isCreateLayout, layoutName)));
		Objects.requireNonNull(PsiManager.getInstance(project).findDirectory(directory.getVirtualFile())).add(javaFile);
	}

	// 根据代码创建Java类
	private void createJavaClassFromCode(PsiPackage psiPackage, PsiDirectory directory, int type, String className, boolean isCreateViewModel,String viewModelPackage, String viewModelClassName, boolean isCreateLayout,
			String layoutName) throws Exception {
		PsiFile javaFile = PsiFileFactory.getInstance(project).createFileFromText(className + ".java", JavaFileType.INSTANCE,
				type == 1 ? CodeTool.KsActivityJavaCode(psiPackage.getQualifiedName(), className, isCreateViewModel, viewModelClassName, isCreateLayout, layoutName)
						: type == 2 ? CodeTool.KsFragmentJavaCode(psiPackage.getQualifiedName(), className, isCreateViewModel, viewModelClassName, isCreateLayout, layoutName)
								: CodeTool.KsActivityJavaCode(directory.getVirtualFile().getName(), className, isCreateViewModel, viewModelClassName, isCreateLayout, layoutName));
		directory.add(javaFile);
	}

	// 根据代码创建xml
	private void createXmlFromCode(String className) throws Exception {
		// 没有就创建一个，第一次使用代码字符串创建个类
		PsiFile javaFile = PsiFileFactory.getInstance(project).createFileFromText(className.toLowerCase() + ".xml", JavaFileType.INSTANCE, CodeTool.KsViewXmlCode());
		Objects.requireNonNull(PsiManager.getInstance(project).findDirectory(Objects.requireNonNull(DirTool.getAppLayoutDir(event)))).add(javaFile);
	}

	// 开始写
	public void writeAction(int type, String className, boolean isCreateViewModel, String viewModelClassName, boolean isCreateLayout, String layoutName, String viewModelPackage,
			String repositoryPackage, String repositoryClassName, String serviceClassName, boolean isKotlin) {
		WriteCommandAction.runWriteCommandAction(project, () -> {
			try {
				createClassFromCode(type, className, isCreateViewModel, viewModelClassName, isCreateLayout, layoutName, viewModelPackage, repositoryPackage, repositoryClassName, serviceClassName,
						isKotlin);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private void writerFile(PsiDirectory directory,String viewModelPackage, String viewModelClassName, String repositoryPackage, String repositoryClassName, String serviceClassName, boolean isKotlin) {
		String viewModelPath = DirTool.getJavaDir(directory) + "/" + viewModelPackage.replaceAll("\\.", "/");
		File file = new File(viewModelPath);
		if (!file.exists()) {// 如果文件夹不存在
			file.mkdir();// 创建文件夹
		}
		String repositoryPath = DirTool.getJavaDir(directory) + "/" + repositoryPackage.replaceAll("\\.", "/");
		File file1 = new File(repositoryPath);
		if (!file1.exists()) {// 如果文件夹不存在
			file1.mkdir();// 创建文件夹
		}
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(DirTool.getJavaDir(directory) + "/" + viewModelPackage.replaceAll("\\.", "/") + "/" + viewModelClassName + (isKotlin ? ".kt" : ".java")));
			writer.write(CodeTool.KsViewModelKotlinCode(viewModelPackage, viewModelClassName, repositoryPackage, repositoryClassName));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedWriter writer1;
		try {
			writer1 = new BufferedWriter(new FileWriter(DirTool.getJavaDir(directory) + "/" + repositoryPackage.replaceAll("\\.", "/") + "/" + repositoryClassName + (isKotlin ? ".kt" : ".java")));
			writer1.write(CodeTool.KsRepositoryKotlinCode(repositoryPackage, repositoryClassName, serviceClassName));
			writer1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedWriter writer2;
		try {
			writer2 = new BufferedWriter(new FileWriter(DirTool.getJavaDir(directory) + "/" + repositoryPackage.replaceAll("\\.", "/") + "/" + serviceClassName + (isKotlin ? ".kt" : ".java")));
			writer2.write(CodeTool.KsServiceKotlinCode(repositoryPackage, serviceClassName));
			writer2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
