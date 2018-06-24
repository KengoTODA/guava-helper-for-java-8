package jp.skypencil.guava;

import java.util.Arrays;
import org.sonar.api.Plugin;

public class SonarQubePlugin implements Plugin {
  @Override
  public void define(Context context) {
    context.addExtensions(Arrays.asList(SonarQubeRulesDefinition.class));
  }
}
