package driver;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TableModel extends AbstractTableModel {
    private final List<String> keys;
    private final Map<String, String> data;

    public TableModel(Map<String, String> data) {
        this.data = data;
        this.keys = new ArrayList<>(data.keySet());
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String key = keys.get(rowIndex);
        if (columnIndex == 0) {
            return key;
        } else {
            return data.get(key);
        }
    }

    @Override
    public String getColumnName(int column) {
        return column == 0 ? "Field" : "Value";
    }
}

