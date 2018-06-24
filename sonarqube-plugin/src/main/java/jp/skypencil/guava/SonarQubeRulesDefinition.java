package jp.skypencil.guava;

import org.sonar.plugins.java.Java;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionXmlLoader;

public class SonarQubeRulesDefinition implements RulesDefinition {
  public static final String REPOSITORY_KEY = "guava-helper";
  public static final String REPOSITORY_NAME = "GuavaHelper";
  public static final int RULE_COUNT = 1;

  @Override
  public void define(Context context) {
    NewRepository repository =
        context.createRepository(REPOSITORY_KEY, Java.KEY).setName(REPOSITORY_NAME);

    RulesDefinitionXmlLoader ruleLoader = new RulesDefinitionXmlLoader();
    ruleLoader.load(
        repository,
        SonarQubeRulesDefinition.class.getResourceAsStream(
            "/jp/skypencil/guava/rules.xml"),
        "UTF-8");
    repository.done();
  }
}
