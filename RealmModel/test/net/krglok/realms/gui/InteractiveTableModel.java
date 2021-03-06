package net.krglok.realms.gui;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class InteractiveTableModel extends AbstractTableModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2708136973063403181L;
	public static final int TITLE_INDEX = 0;
    public static final int ARTIST_INDEX = 1;
    public static final int ALBUM_INDEX = 2;
    public static final int HIDDEN_INDEX = 3;

    protected String[] columnNames;
    protected Vector dataVector;

    public InteractiveTableModel(String[] columnNames) {
        this.columnNames = columnNames;
        dataVector = new Vector();
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }

    public boolean isCellEditable(int row, int column) {
        if (column == HIDDEN_INDEX) return false;
        else return true;
    }

    public Class getColumnClass(int column) {
        switch (column) {
            case TITLE_INDEX:
            case ARTIST_INDEX:
            case ALBUM_INDEX:
               return String.class;
            default:
               return Object.class;
        }
    }

    public Object getValueAt(int row, int column) {
    	DataRecord record = (DataRecord)dataVector.get(row);
        switch (column) {
            case TITLE_INDEX:
               return record.getName();
            case ARTIST_INDEX:
               return record.getAmount();
            case ALBUM_INDEX:
               return record.getValue();
            default:
               return new Object();
        }
    }

    public void setValueAt(Object value, int row, int column) {
    	DataRecord record = (DataRecord)dataVector.get(row);
        switch (column) {
            case TITLE_INDEX:
               record.setName((String)value);
               break;
            case ARTIST_INDEX:
               record.setAmount((String)value);
               break;
            case ALBUM_INDEX:
               record.setValue((String)value);
               break;
            default:
               System.out.println("invalid index");
        }
        fireTableCellUpdated(row, column);
    }

    public int getRowCount() {
        return dataVector.size();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public boolean hasEmptyRow() {
        if (dataVector.size() == 0) return false;
        DataRecord audioRecord = (DataRecord)dataVector.get(dataVector.size() - 1);
        if (audioRecord.getName().trim().equals("") &&
           audioRecord.getAmount().trim().equals("") &&
           audioRecord.getValue().trim().equals(""))
        {
           return true;
        }
        else return false;
    }

    public void addEmptyRow() {
        dataVector.add(new DataRecord());
        fireTableRowsInserted(
           dataVector.size() - 1,
           dataVector.size() - 1);
    }
}
