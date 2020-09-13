package com.ks.plugin.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.ks.plugin.dialog.KsFormatDialog;
import org.jetbrains.annotations.NotNull;

/**
 * @author Wiser
 * 
 *         Action
 */
public class KsAction extends AnAction {

	KsAction() {
		super("Ks Format Create");
	}

	public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
//		KsDialog.showKsDialog();
		KsFormatDialog.showKsFormatDialog(anActionEvent);
	}
}
