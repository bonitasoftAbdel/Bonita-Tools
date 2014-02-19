package org.bonitasoft.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yhon Acurio
 * 
 */
public class BonitaDBTools {
	/**
	 * Get the first element from the first column from a table
	 * 
	 * @param resultSet
	 * @return an object
	 * @throws SQLException
	 */
	public static Object getObject(ResultSet resultSet) throws SQLException {
		Object result = null;
		if (resultSet.next()) {
			result = resultSet.getObject(1);
		}
		return result;
	}

	/**
	 * Get a list of elements from a table
	 * 
	 * @param resultSet
	 * @param columnName
	 * @return
	 * @throws SQLException
	 */
	public static List<Object> toList(ResultSet resultSet, String columnName)
			throws SQLException {
		List<Object> result = new ArrayList<Object>();
		while (resultSet.next()) {
			result.add(resultSet.getObject(columnName));
		}
		return result;
	}

	/**
	 * Get a map of elements from a table (key, value)
	 * 
	 * @param resultSet
	 * @param columnNameForKey
	 *            Column name of table key
	 * @param columnNameForLabel
	 *            Column name of table label
	 * @return
	 * @throws SQLException
	 */
	public static Map<Object, Object> toKeyValueMap(ResultSet resultSet,
			String columnNameForKey, String columnNameForLabel)
			throws SQLException {
		LinkedHashMap<Object, Object> result = new LinkedHashMap<Object, Object>();
		while (resultSet.next()) {
			result.put(resultSet.getObject(columnNameForLabel),
					resultSet.getObject(columnNameForKey));
		}
		return result;
	}

	/**
	 * Get a table into a list of map format
	 * 
	 * @param resultSet
	 * @return A list of map. A row will be an element of the list. Ex:
	 *         [[column1name
	 *         :row1column1value,column2name:row1column2value],[column1name
	 *         :row2column1value,column2name:row2column2value]]
	 * @throws SQLException
	 */
	public static List<Map<Object, Object>> toListOfMap(ResultSet resultSet)
			throws SQLException {
		List<Map<Object, Object>> result = new ArrayList<Map<Object, Object>>();
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		int columnsNumber = resultSetMetaData.getColumnCount();

		while (resultSet.next()) {
			LinkedHashMap<Object, Object> map = new LinkedHashMap<Object, Object>();
			if (columnsNumber > 0) {
				for (int i = 0; i < columnsNumber; i++) {
					map.put(resultSetMetaData.getColumnName(i + 1),
							resultSet.getObject(i + 1));
				}
			}
			result.add(map);
		}
		return result;
	}

	/**
	 * Convert a query result into a map
	 * 
	 * @param resultSet
	 * @return A map. A row will be binded to the map. Ex: [column1name
	 *         :row1column1value,column2name:row1column2value]
	 * @throws SQLException
	 */
	public static Map<Object, Object> toMap(ResultSet resultSet)
			throws SQLException {
		List<Map<Object, Object>> list = toListOfMap(resultSet);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	/**
	 * Get a table into a list of list format
	 * 
	 * @param resultSet
	 * @return A list of map. A row will be an element of the list. Ex:
	 *         [[row1column1value
	 *         ,row1column2value],[row2column1value,row2column2value]]
	 * @throws SQLException
	 */
	public static List<List<Object>> toListOfList(ResultSet resultSet)
			throws SQLException {
		List<List<Object>> result = new ArrayList<List<Object>>();
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		int columnsNumber = resultSetMetaData.getColumnCount();

		while (resultSet.next()) {
			List<Object> list = new ArrayList<Object>();
			if (columnsNumber > 0) {
				for (int i = 0; i < columnsNumber; i++) {
					list.add(resultSet.getObject(i + 1));
				}
			}
			result.add(list);
		}
		return result;
	}

}