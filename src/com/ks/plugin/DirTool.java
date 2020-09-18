package com.ks.plugin;

import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiPackage;

/**
 * @author Wiser
 *
 *      路径工具
 */
public class DirTool {

    /**
     * 获取鼠标选中的目录
     * @param event
     * @return
     */
    public static PsiDirectory getMouseDir(AnActionEvent event){
        // 获取鼠标选中的目录
        IdeView ideView = event.getRequiredData(LangDataKeys.IDE_VIEW);
        return ideView.getOrChooseDirectory();
    }

    /**
     * 获取鼠标选中的目录包
     * @param event
     * @return
     */
    public static PsiPackage getPsiPackage(AnActionEvent event){
        return JavaDirectoryService.getInstance().getPackage(getMouseDir(event));
    }

    /**
     * 获取Base dir 路径
     * @param project
     * @return
     */
    public static String getAppBaseDirStr(Project project){
        return project.getBasePath();
    }

    /**
     * 获取base dir File
     * @param project
     * @return
     */
    public static VirtualFile getAppBaseDirFile(Project project){
        return LocalFileSystem.getInstance().findFileByPath(getAppBaseDirStr(project));
    }

    /**
     * 获取APP layout路径
     * @param event
     * @return /app/src/main/res/layout
     */
    public static VirtualFile getAppLayoutDir(AnActionEvent event){
        Module module = event.getRequiredData(LangDataKeys.MODULE);
        VirtualFile baseDir = module.getModuleFile().getParent();
        try{
            VirtualFile srcFile = baseDir.findChild("src");
            if (srcFile == null)
                srcFile = baseDir.createChildDirectory(null,"src");

            VirtualFile mainFile = srcFile.findChild("main");
            if (mainFile == null)
                mainFile = srcFile.createChildDirectory(null,"main");

            VirtualFile resFile = mainFile.findChild("res");
            if (resFile == null)
                resFile = mainFile.createChildDirectory(null,"res");

            VirtualFile layoutFile = resFile.findChild("layout");
            if (layoutFile == null)
                layoutFile = resFile.createChildDirectory(null,"layout");

            return layoutFile;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取APP Java路径
     * @param directory
     * @return /app/src/main/res/layout
     */
    public static String getJavaDir(PsiDirectory directory){
        String path = directory.getVirtualFile().getCanonicalPath();
        return path.substring(0,path.lastIndexOf("/src/main/java/")) + "/src/main/java/";
//        try{
//            VirtualFile srcFile = baseDir.findChild("src");
//            if (srcFile == null)
//                srcFile = baseDir.createChildDirectory(null,"src");
//
//            VirtualFile mainFile = srcFile.findChild("main");
//            if (mainFile == null)
//                mainFile = srcFile.createChildDirectory(null,"main");
//
//            VirtualFile javaFile = mainFile.findChild("java");
//            if (javaFile == null)
//                javaFile = mainFile.createChildDirectory(null,"java");
//            return javaFile;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }
}
