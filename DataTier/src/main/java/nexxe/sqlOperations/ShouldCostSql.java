package nexxe.sqlOperations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import nexxe.data.utils.DataTierUtils;
import nexxe.dbDTO.ShouldCostDTO;


public class ShouldCostSql {
	private static ShouldCostSql instance;

	public static synchronized ShouldCostSql getInstance() {

		if (instance == null) {
			instance = new ShouldCostSql();
		}
		return instance;
	}

	public Set<ShouldCostDTO> getCurrency() {
		Connection connection = DataTierUtils.conn;
		Set<ShouldCostDTO> currency = new HashSet<ShouldCostDTO>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt
					.executeQuery("Select CurrencyCode from [dbo].[SCost_Documents] where  CurrencyCode != ''");

			while (rs.next()) {
				ShouldCostDTO user = extractCurrencyFromResultSet(rs);
				currency.add(user);
				System.out.println(user.getCurrencyCode());
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return currency;
	}

	private ShouldCostDTO extractCurrencyFromResultSet(ResultSet rs) throws SQLException {
		ShouldCostDTO currency = new ShouldCostDTO();
		currency.setCurrencyCode(rs.getString("CurrencyCode"));
		return currency;
	}

}
