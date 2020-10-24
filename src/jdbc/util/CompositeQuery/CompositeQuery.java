package jdbc.util.CompositeQuery;

import java.sql.Timestamp;
import java.util.*;

public class CompositeQuery {

	public static String getCondition(String columnName, String value, String... value2) {

		String aCondition = null;

		if ("meal_order_no".equals(columnName) || "emp_no".equals(columnName) || "mem_no".equals(columnName)
				|| "meal_order_sts".equals(columnName) || "noti_sts".equals(columnName) || "pay_sts".equals(columnName)) // �Ω��L
			aCondition = columnName + "=" + "'" + value + "'";
		else if ("order_time".equals(columnName) || "pickup_time".equals(columnName))
			aCondition = columnName + " between " + "to_timestamp('" + value + "','yyyy-mm-dd hh24:mi')" + " and "
					+ "to_timestamp('" + value2[0] + "','yyyy-mm-dd hh24:mi')";
		// to_timestamp('2020-10-15 18:14','yyyy-mm-dd hh24:mi') and
		// to_timestamp('2020-10-17 18:17','yyyy-mm-dd hh24:mi')
		return aCondition + " ";
	}

	public static String getWhereCondition(Map<String, String[]> map) {
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		int count = 0;
		for (String key : keys) {

			if (map.get(key).length > 1) {
//				System.out.println(key);
				String value = map.get(key)[0];
				String value2 = map.get(key)[1];

				if (value != null && value2 != null && value.length() != 0 && value2.length() != 0) {
//				System.out.println("value1" + value);
//				System.out.println("value2" + value2);
					count++;
					String aCondition = getCondition(key, value.trim(), value2.trim());

					if (count == 1)
						whereCondition.append(" where " + aCondition);
					else
						whereCondition.append(" and " + aCondition);
				}

			} else {
				String value = map.get(key)[0];
//				System.out.println(key);
//				System.out.println(value);
				if (value != null && value.trim().length() != 0 && !"action".equals(key)) {
					count++;
					String aCondition = getCondition(key, value.trim());

					if (count == 1)
						whereCondition.append(" where " + aCondition);
					else
						whereCondition.append(" and " + aCondition);

				}
			}
		}

		return whereCondition.toString();
	}
}
