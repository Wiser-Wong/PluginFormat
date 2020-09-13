package com.ks.plugin.action;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Wiser
 *
 *         Action 组
 */
public class KsActionGroup extends ActionGroup {

	@NotNull @Override public AnAction[] getChildren(@Nullable AnActionEvent anActionEvent) {
		return new AnAction[] { new KsAction() };
	}
}
