package org.herac.tuxguitar.app.view.menu.impl;

import org.herac.tuxguitar.app.TuxGuitar;
import org.herac.tuxguitar.app.action.impl.edit.TGCutAction;
import org.herac.tuxguitar.app.action.impl.edit.TGCopyAction;
import org.herac.tuxguitar.app.action.impl.edit.TGPasteAction;
import org.herac.tuxguitar.app.action.impl.edit.TGRepeatAction;
import org.herac.tuxguitar.app.action.impl.edit.TGSetMouseModeEditionAction;
import org.herac.tuxguitar.app.action.impl.edit.TGSetMouseModeSelectionAction;
import org.herac.tuxguitar.app.action.impl.edit.TGSetNaturalKeyAction;
import org.herac.tuxguitar.app.action.impl.edit.TGSetVoice1Action;
import org.herac.tuxguitar.app.action.impl.edit.TGSetVoice2Action;
import org.herac.tuxguitar.app.view.component.tab.Tablature;
import org.herac.tuxguitar.app.view.component.tab.TablatureEditor;
import org.herac.tuxguitar.app.view.component.tab.edit.EditorKit;
import org.herac.tuxguitar.app.view.menu.TGMenuItem;
import org.herac.tuxguitar.editor.action.edit.TGRedoAction;
import org.herac.tuxguitar.editor.action.edit.TGUndoAction;
import org.herac.tuxguitar.editor.clipboard.TGClipboard;
import org.herac.tuxguitar.ui.menu.UIMenu;
import org.herac.tuxguitar.ui.menu.UIMenuActionItem;
import org.herac.tuxguitar.ui.menu.UIMenuCheckableItem;
import org.herac.tuxguitar.ui.menu.UIMenuSubMenuItem;

public class EditMenuItem extends TGMenuItem{

	private UIMenuSubMenuItem editMenuItem;
	private UIMenuActionItem cut;
	private UIMenuActionItem copy;
	private UIMenuActionItem paste;
	private UIMenuActionItem repeat;
	private UIMenuActionItem undo;
	private UIMenuActionItem redo;
	private UIMenuCheckableItem modeSelection;
	private UIMenuCheckableItem modeEdition;
	private UIMenuCheckableItem notNaturalKey;
	private UIMenuCheckableItem voice1;
	private UIMenuCheckableItem voice2;

	public EditMenuItem(UIMenu parent) {
		this.editMenuItem = parent.createSubMenuItem();
	}

	public void showItems() {
		//--CUT--
		this.cut = this.editMenuItem.getMenu().createActionItem();
		this.cut.addSelectionListener(this.createActionProcessor(TGCutAction.NAME));

		//--COPY--
		this.copy = this.editMenuItem.getMenu().createActionItem();
		this.copy.addSelectionListener(this.createActionProcessor(TGCopyAction.NAME));

		//--PASTE--
		this.paste = this.editMenuItem.getMenu().createActionItem();
		this.paste.addSelectionListener(this.createActionProcessor(TGPasteAction.NAME));

		//--REPEAT--
		this.repeat = this.editMenuItem.getMenu().createActionItem();
		this.repeat.addSelectionListener(this.createActionProcessor(TGRepeatAction.NAME));

		//--SEPARATOR--
		this.editMenuItem.getMenu().createSeparator();

		//--UNDO--
		this.undo = this.editMenuItem.getMenu().createActionItem();
		this.undo.addSelectionListener(this.createActionProcessor(TGUndoAction.NAME));

		//--REDO--
		this.redo = this.editMenuItem.getMenu().createActionItem();
		this.redo.addSelectionListener(this.createActionProcessor(TGRedoAction.NAME));

		//--SEPARATOR--
		this.editMenuItem.getMenu().createSeparator();

		//--TABLATURE EDIT MODE--
		this.modeSelection = this.editMenuItem.getMenu().createRadioItem();
		this.modeSelection.addSelectionListener(this.createActionProcessor(TGSetMouseModeSelectionAction.NAME));

		//--SCORE EDIT MODE--
		this.modeEdition = this.editMenuItem.getMenu().createRadioItem();
		this.modeEdition.addSelectionListener(this.createActionProcessor(TGSetMouseModeEditionAction.NAME));

		//--NATURAL NOTES--
		this.notNaturalKey = this.editMenuItem.getMenu().createCheckItem();
		this.notNaturalKey.addSelectionListener(this.createActionProcessor(TGSetNaturalKeyAction.NAME));

		//--SEPARATOR--
		this.editMenuItem.getMenu().createSeparator();

		//--VOICE 1--
		this.voice1 = this.editMenuItem.getMenu().createRadioItem();
		this.voice1.addSelectionListener(this.createActionProcessor(TGSetVoice1Action.NAME));

		//--VOICE 2--
		this.voice2 = this.editMenuItem.getMenu().createRadioItem();
		this.voice2.addSelectionListener(this.createActionProcessor(TGSetVoice2Action.NAME));

		this.loadIcons();
		this.loadProperties();
	}

	public void update(){
		EditorKit kit = TuxGuitar.getInstance().getTablatureEditor().getTablature().getEditorKit();
		Tablature tablature = TablatureEditor.getInstance(findContext()).getTablature();
		boolean running = TuxGuitar.getInstance().getPlayer().isRunning();
		this.cut.setEnabled(!running && tablature.getSelector().isActive());
		this.copy.setEnabled(!running);
		this.paste.setEnabled(!running && TGClipboard.getInstance(findContext()).hasContents());
		this.repeat.setEnabled(!running && tablature.getSelector().isActive());
		this.undo.setEnabled(!running && TuxGuitar.getInstance().getUndoableManager().canUndo());
		this.redo.setEnabled(!running && TuxGuitar.getInstance().getUndoableManager().canRedo());
		this.modeSelection.setChecked(kit.getMouseMode() == EditorKit.MOUSE_MODE_SELECTION);
		this.modeSelection.setEnabled(!running);
		this.modeEdition.setChecked(kit.getMouseMode() == EditorKit.MOUSE_MODE_EDITION);
		this.modeEdition.setEnabled(!running);
		this.notNaturalKey.setChecked(!kit.isNatural());
		this.notNaturalKey.setEnabled(!running && kit.getMouseMode() == EditorKit.MOUSE_MODE_EDITION);
		this.voice1.setChecked(kit.getTablature().getCaret().getVoice() == 0);
		this.voice2.setChecked(kit.getTablature().getCaret().getVoice() == 1);
	}

	public void loadProperties(){
		setMenuItemTextAndAccelerator(this.editMenuItem, "edit.menu", null);
		setMenuItemTextAndAccelerator(this.cut, "edit.cut", TGCutAction.NAME);
		setMenuItemTextAndAccelerator(this.copy, "edit.copy", TGCopyAction.NAME);
		setMenuItemTextAndAccelerator(this.paste, "edit.paste", TGPasteAction.NAME);
		setMenuItemTextAndAccelerator(this.repeat, "edit.repeat", TGRepeatAction.NAME);
		setMenuItemTextAndAccelerator(this.undo, "edit.undo", TGUndoAction.NAME);
		setMenuItemTextAndAccelerator(this.redo, "edit.redo", TGRedoAction.NAME);
		setMenuItemTextAndAccelerator(this.modeSelection, "edit.mouse-mode-selection", TGSetMouseModeSelectionAction.NAME);
		setMenuItemTextAndAccelerator(this.modeEdition, "edit.mouse-mode-edition", TGSetMouseModeEditionAction.NAME);
		setMenuItemTextAndAccelerator(this.notNaturalKey, "edit.not-natural-key", TGSetNaturalKeyAction.NAME);
		setMenuItemTextAndAccelerator(this.voice1, "edit.voice-1", TGSetVoice1Action.NAME);
		setMenuItemTextAndAccelerator(this.voice2, "edit.voice-2", TGSetVoice2Action.NAME);
	}

	public void loadIcons(){
		this.cut.setImage(TuxGuitar.getInstance().getIconManager().getEditCut());
		this.copy.setImage(TuxGuitar.getInstance().getIconManager().getEditCopy());
		this.paste.setImage(TuxGuitar.getInstance().getIconManager().getEditPaste());
		this.repeat.setImage(TuxGuitar.getInstance().getIconManager().getEditRepeat());
		this.undo.setImage(TuxGuitar.getInstance().getIconManager().getEditUndo());
		this.redo.setImage(TuxGuitar.getInstance().getIconManager().getEditRedo());
		this.modeSelection.setImage(TuxGuitar.getInstance().getIconManager().getEditModeSelection());
		this.modeEdition.setImage(TuxGuitar.getInstance().getIconManager().getEditModeEdition());
		this.notNaturalKey.setImage(TuxGuitar.getInstance().getIconManager().getEditModeEditionNotNatural());
		this.voice1.setImage(TuxGuitar.getInstance().getIconManager().getEditVoice1());
		this.voice2.setImage(TuxGuitar.getInstance().getIconManager().getEditVoice2());
	}
}
