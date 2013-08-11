package org.controlsfx.control.spreadsheet.skin;

import javafx.scene.control.TableColumnBase;

import com.sun.javafx.scene.control.skin.NestedTableColumnHeader;
import com.sun.javafx.scene.control.skin.TableColumnHeader;
import com.sun.javafx.scene.control.skin.TableViewSkinBase;

public class SpreadsheetNestedTableColumnHeader extends NestedTableColumnHeader{

	public SpreadsheetNestedTableColumnHeader(TableViewSkinBase<?, ?, ?, ?, ?, ?> skin,
			TableColumnBase<?, ?> tc) {
		super(skin, tc);
	}

	@Override
	protected TableColumnHeader createTableColumnHeader(TableColumnBase col) {
		return col.getColumns().isEmpty() ?
				new TableColumnHeader(getTableViewSkin(), col) :
					new SpreadsheetNestedTableColumnHeader(getTableViewSkin(), col);
	}
	
	@Override protected void layoutChildren() {
		super.layoutChildren();
		/*****************************************************************
		 * 				MODIFIED BY NELLARMONIA
		 *****************************************************************/
		layoutFixedColumns();
		/*****************************************************************
		 * 				END OF MODIFIED BY NELLARMONIA
		 *****************************************************************/
	}
	
	/**
	 * We want ColumnHeader to be fixed when we freeze some columns
	 * @param scrollX
	 */
	public void layoutFixedColumns() {
		double scrollX = ((SpreadsheetViewSkin)getTableViewSkin()).spreadsheetView.getHbar().getValue();
		final double h = getHeight() - snappedTopInset() - snappedBottomInset();

		final int labelHeight = (int) getChildren().get(0).prefHeight(-1);
		for(int j =0; j< ((SpreadsheetViewSkin)getTableViewSkin()).spreadsheetView.getFixedColumns().size();++j){
			final TableColumnHeader n = getColumnHeaders().get(j);
			n.toFront();
			final double prefWidth = snapSize(n.prefWidth(-1));
			//            double prefHeight = n.prefHeight(-1);

			// position the column header in the default location...
			n.resize(prefWidth, snapSize(h - labelHeight));
			n.relocate(scrollX, labelHeight + snappedTopInset());


			// shuffle along the x-axis appropriately
			scrollX += prefWidth;
		}

	}
	
	public void updateHeader(){
		setHeadersNeedUpdate();
	}

}