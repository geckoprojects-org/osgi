package org.osgi.test.cases.feature.junit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.osgi.test.cases.feature.assertj.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.osgi.test.cases.feature.assertj.FeatureAssert;
import org.osgi.service.feature.BuilderFactory;
import org.osgi.service.feature.Feature;
import org.osgi.service.feature.FeatureBuilder;
import org.osgi.service.feature.FeatureBundle;
import org.osgi.service.feature.FeatureConfiguration;
import org.osgi.service.feature.FeatureExtension;
import org.osgi.service.feature.Features;
import org.osgi.service.feature.ID;

public class FeatureTest {

	@Test
	void Feature_isNotNull() throws Exception {
		FeatureBuilder featureBuilder = createFeatureBuilderGAV();
		Feature feature = featureBuilder.build();
		FeatureAssert featureAssert = assertThat(feature);
		featureAssert.isNotNull();
		featureAssert.hasConfigurationsThat().isNotNull().isEmpty();
		featureAssert.hasExtensionsThat().isNotNull().isEmpty();
		featureAssert.hasVariablesThat().isNotNull().isEmpty();
		featureAssert.hasBundlesThat().isNotNull().isEmpty();

	}

	@Test
	void Feature_isImmutable() throws Exception {
		FeatureBuilder featureBuilder = createFeatureBuilderGAV();
		Feature feature = featureBuilder.build();

		// not sure that UnsupportedOperationException is thrown
		assertThrows(UnsupportedOperationException.class, () -> {
			feature.getBundles().add(mock(FeatureBundle.class));
		});

		assertThrows(UnsupportedOperationException.class, () -> {
			feature.getExtensions().put("foo", mock(FeatureExtension.class));
		});

		assertThrows(UnsupportedOperationException.class, () -> {
			feature.getConfigurations()
					.put("foo", mock(FeatureConfiguration.class));
		});

		assertThrows(UnsupportedOperationException.class, () -> {
			feature.getVariables().put("foo", "bar");
		});

	}

	private FeatureBuilder createFeatureBuilderGAV() {
		BuilderFactory builderFactory = Features.getBuilderFactory();
		FeatureBuilder featureBuilder = builderFactory
				.newFeatureBuilder(ID.fromMavenID("g:a:v"));
		return featureBuilder;
	}

}
