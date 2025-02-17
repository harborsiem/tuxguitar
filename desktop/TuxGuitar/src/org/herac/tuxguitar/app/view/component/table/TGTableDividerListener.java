package org.herac.tuxguitar.app.view.component.table;

import org.herac.tuxguitar.ui.event.UIMouseDragListener;
import org.herac.tuxguitar.ui.event.UIMouseEvent;
import org.herac.tuxguitar.ui.layout.UITableLayout;
import org.herac.tuxguitar.ui.resource.UISize;
import org.herac.tuxguitar.ui.widget.UIControl;

public class TGTableDividerListener implements UIMouseDragListener {
	
	public static final String LEFT_CONTROL = "leftControl";
	public static final String RIGHT_CONTROL = "rightControl";
	
	private TGTable table;
	private TGTableHeader leftColumn;
	private TGTableHeader rightColumn;
	
	public TGTableDividerListener(TGTable table, TGTableHeader leftColumn, TGTableHeader rightColumn) {
		this.table = table;
		this.leftColumn = leftColumn;
		this.rightColumn = rightColumn;
	}

	public void onMouseDrag(UIMouseEvent event) {
		float move = event.getPosition().getX();
		
		Float leftWidth = this.computeWidth(this.leftColumn.getControl(), move);
		Float rightWidth = this.computeWidth(this.rightColumn.getControl(), -move);
		if( leftWidth != null && rightWidth != null ) {
			UITableLayout uiLayout = (UITableLayout) this.table.getColumnControl().getLayout();
			uiLayout.set(this.leftColumn.getControl(), UITableLayout.MINIMUM_PACKED_WIDTH, leftWidth);
			uiLayout.set(this.rightColumn.getControl(), UITableLayout.MINIMUM_PACKED_WIDTH, rightWidth);
			
			this.table.update();
		}
	}
	
	private Float computeWidth(UIControl control, float move) {
		UISize currentPackedSize = control.getPackedSize();
		
		control.computePackedSize(null, null);
		UISize computedPackedSize = control.getPackedSize();
		
		control.computePackedSize(currentPackedSize.getWidth(), currentPackedSize.getHeight());
		
		float newWidth = (control.getBounds().getWidth() + move);
		if( newWidth >= computedPackedSize.getWidth()) {
			return newWidth;
		}
		return null;
	}
}
