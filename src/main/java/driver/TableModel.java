package driver;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class to construct a table model from a hashmap
 */
public class TableModel extends AbstractTableModel {
    private final List<String> keys;
    private final Map<String, String> data;

    /**
     * Constructor for the table model class
     *
     * @param data  A map from string to string consisting of the contents of the table
     */
    public TableModel(Map<String, String> data) {
        this.data = data;
        this.keys = new ArrayList<>(data.keySet());
    }

    /**
     * @return  returns the number of rows in the table (length of hashmap)
     */
    @Override
    public int getRowCount() {
        return data.size();
    }

    /**
     * @return  returns 2 (since when constructed from a hashmap,
     *          the keys are one column and the values are the other)
     */
    @Override
    public int getColumnCount() {
        return 2;
    }

    /**
     * Gets a value from a particular row and column
     *
     * @param rowIndex        the row whose value is to be queried
     * @param columnIndex     the column whose value is to be queried
     * @return                returns the object at the queried coordinate
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String key = keys.get(rowIndex);
        if (columnIndex == 0) {
            return key;
        } else {
            return data.get(key);
        }
    }

    /**
     * @param column  the column being queried
     * @return        returns either field or value, based on if the column argument
     *                is 0 or any other value
     */
    @Override
    public String getColumnName(int column) {
        return column == 0 ? "Field" : "Value";
    }
}

