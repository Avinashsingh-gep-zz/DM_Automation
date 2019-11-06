package dm.webTests.objectFactory;

import dm.pages.HomePage;
import dm.pages.Login;
import dm.pages.ShouldCostPage;
import dm.sqlOperations.ShouldCostSql;
import dm.testtier.utils.Keys;
import dm.testtier.utils.ScenarioContext;

public class ObjectFactory {

	protected Login login;
	protected HomePage homePage;
	protected ShouldCostPage scPage;
	protected ShouldCostSql scSql;

	public ObjectFactory() {
		login = Login.getInstance(ScenarioContext.getContext(Keys.WebDriver));
		homePage = HomePage.getInstance(ScenarioContext.getContext(Keys.WebDriver));
		scPage = ShouldCostPage.getInstance(ScenarioContext.getContext(Keys.WebDriver));
		scSql = ShouldCostSql.getInstance();
	}

}
