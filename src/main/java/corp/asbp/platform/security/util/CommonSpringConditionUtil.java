package corp.asbp.platform.security.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;

public class CommonSpringConditionUtil {
	private static Logger LOGGER = LoggerFactory.getLogger(CommonSpringConditionUtil.class);
	
	public static boolean isConditionSatisfied(ConditionContext context, String propertyKey, Boolean expectedValue) {
		Environment env = context.getEnvironment();
		if (env != null) {
			String propertyValue = env.getProperty(propertyKey);
			LOGGER.info(propertyKey + ": " + propertyValue);
			if (propertyValue != null && expectedValue.equals(new Boolean(propertyValue.toLowerCase().trim()))) {
				return true;
			}
		}
		return false;
	}
}
