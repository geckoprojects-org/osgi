package org.osgi.test.cases.feature.junit;

import static org.junit.Assert.assertThrows;
import static org.osgi.test.cases.feature.assertj.Assertions.assertThat;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.osgi.test.cases.feature.assertj.FeatureAssert;
import org.osgi.service.feature.BuilderFactory;
import org.osgi.service.feature.Feature;
import org.osgi.service.feature.FeatureBuilder;
import org.osgi.service.feature.Features;
import org.osgi.service.feature.ID;

@Disabled
public class FeaturesTest {
	static final ID	ID_GAV		= new ID("g", "a", "v");
	static final ID	ID_GAVT		= new ID("g", "a", "v", "t", null);
	static final ID	ID_GAVTC	= new ID("g", "a", "v", "t", "c");

	@Nested
	class BuilderFactoryTest {

		@Test
		void getBuilderFactory_isNotNull() throws Exception {
			BuilderFactory builderFactory = Features.getBuilderFactory();
			Assertions.assertThat(builderFactory).isNotNull();
		}

		@Nested
		class FeatureBundleBuilderTest {

			@Test
			void addMetadata_nullKey_must_fail() throws Exception {

				Assertions
						.assertThatExceptionOfType(
								IllegalArgumentException.class)
						.isThrownBy(() -> {
							Features.getBuilderFactory()
									.newBundleBuilder(ID_GAV)
									.addMetadata(null, "value");
						})
						.withMessageMatching(".*");
			}

			@ParameterizedTest
			@ValueSource(strings = {
					"id", "Id", "iD", "ID"
			})
			void addMetadata_id_must_fail(String id) throws Exception {

				Assertions
						.assertThatExceptionOfType(
								IllegalArgumentException.class)
						.isThrownBy(() -> {
							Features.getBuilderFactory()
									.newBundleBuilder(ID_GAV)
									.addMetadata(id, "value");
						})
						.withMessageMatching(".*");
			}

			// TODO: when sould the exception raise
			@ParameterizedTest
			@ValueSource(strings = {
					"id", "Id", "iD", "ID"
			})
			void build_fail_when_metadata_id(String id) throws Exception {

				Assertions
						.assertThatExceptionOfType(
								IllegalArgumentException.class)
						.isThrownBy(() -> {
							Features.getBuilderFactory()
									.newBundleBuilder(ID_GAV)
									.addMetadata(id, "value")
									.build();
						})
						.withMessageMatching(".*");

			}
		}

	}

	@Nested
	class ReadFeatureTest {

		@Nested
		class ReadFeatureIncompleteTest {
			@Test
			void null_shouldThrow() throws Exception {
				assertThrows(Throwable.class, () -> Features.readFeature(null));
			}

			@Test
			void empty_shouldThrow() throws Exception {
				assertThrows(Throwable.class,
						() -> Features.readFeature(new StringReader("")));
			}

			@Test
			void incomplete_id_shouldThrow() throws Exception {
				assertThrows(Throwable.class,
						() -> Features.readFeature(new StringReader("")));
			}
		}

		@Nested
		class ReadFeatureIDTest {
			@Test
			void read_ID_verson_missing() throws Exception {

				StringWriter sw = new StringWriter();
				JSONWriter jsonWriter = new JSONWriter(sw);

				jsonWriter.object()
						.key("id")
						.object()
						.key("groupId")
						.value("g")
						.endObject()
						.object()
						.key("artifactId")
						.value("a")
						.endObject()
						.endObject();

				assertThrows(Throwable.class, () -> Features
						.readFeature(new StringReader(sw.toString())));
			}

			@Test
			void read_ID_artifact_missing() throws Exception {

				StringWriter sw = new StringWriter();
				JSONWriter jsonWriter = new JSONWriter(sw);

				jsonWriter.object()
						.key("id")
						.object()
						.key("groupId")
						.value("g")
						.endObject()
						.object()
						.key("version")
						.value("v")
						.endObject()
						.endObject();

				assertThrows(Throwable.class, () -> Features
						.readFeature(new StringReader(sw.toString())));
			}

			@Test
			void read_ID_g_null() throws Exception {
				StringWriter sw = new StringWriter();
				JSONWriter jsonWriter = new JSONWriter(sw);

				jsonWriter.object()
						.key("id")
						.object()
						.key("groupId")
						.value(null)
						.endObject()
						.object()
						.key("artifactId")
						.value("a")
						.endObject()
						.object()
						.key("version")
						.value("v")
						.endObject()
						.endObject();

				assertThrows(Throwable.class, () -> Features
						.readFeature(new StringReader(sw.toString())));
			}

			@Test
			void read_ID_a_null() throws Exception {
				StringWriter sw = new StringWriter();
				JSONWriter jsonWriter = new JSONWriter(sw);

				jsonWriter.object()
						.key("id")
						.object()
						.key("groupId")
						.value("g")
						.endObject()
						.object()
						.key("artifactId")
						.value(null)
						.endObject()
						.object()
						.key("version")
						.value("v")
						.endObject()
						.endObject();

				assertThrows(Throwable.class, () -> Features
						.readFeature(new StringReader(sw.toString())));
			}

			@Test
			void read_ID_v_null() throws Exception {
				StringWriter sw = new StringWriter();
				JSONWriter jsonWriter = new JSONWriter(sw);

				jsonWriter.object()
						.key("id")
						.object()
						.key("groupId")
						.value("g")
						.endObject()
						.object()
						.key("artifactId")
						.value("a")
						.endObject()
						.object()
						.key("version")
						.value(null)
						.endObject()
						.endObject();

				assertThrows(Throwable.class, () -> Features
						.readFeature(new StringReader(sw.toString())));
			}

			@Test
			void read_ID_Full() throws Exception {
				StringWriter sw = new StringWriter();
				JSONWriter jsonWriter = new JSONWriter(sw);

				jsonWriter.object()
						.key("id")
						.object()
						.key("groupId")
						.value("g")
						.endObject()
						.object()
						.key("artifactId")
						.value("a")
						.endObject()
						.object()
						.key("version")
						.value("v")
						.key("type")
						.value("t")
						.key("classifier")
						.value("c")
						.endObject()
						.endObject();
				Feature feature = Features
						.readFeature(new StringReader(sw.toString()));

				assertThat(feature).hasIDThat().isEqualTo(ID_GAVTC);

			}
		}

		@Test
		void minimal_read() throws Exception {
			StringWriter sw = new StringWriter();
			JSONWriter jsonWriter = new JSONWriter(sw);

			jsonWriter.object()
					.key("id")
					.object()
					.key("groupId")
					.value("g")
					.endObject()
					.object()
					.key("artifactId")
					.value("a")
					.endObject()
					.object()
					.key("version")
					.value("v")
					.endObject()
					.endObject();

			Feature feature = Features
					.readFeature(new StringReader(sw.toString()));

			FeatureAssert featureAssert = assertThat(feature);
			featureAssert.isNotNull()
					.hasDescription(null)
					.hasLicense(null)
					.hasName(null)
					.hasVendor(null)
					.isComplete();
			featureAssert.hasIDThat().isEqualTo(ID_GAV);
			featureAssert.hasConfigurationsThat().isEmpty();
			featureAssert.hasBundlesThat().isEmpty();
			featureAssert.hasExtensionsThat().isEmpty();
			featureAssert.hasVariablesThat().isEmpty();
		}

	}

	@Nested
	class WriteFeatureTest {
		@Test
		void null_shouldThrow() throws Exception {
			assertThrows(Throwable.class,
					() -> Features.writeFeature(null, null));

		}

		@SuppressWarnings("unchecked")
		@Test
		void write_ID_gav() throws Exception {
			FeatureBuilder featureBuilder = Features.getBuilderFactory()
					.newFeatureBuilder(ID_GAV);
			Feature feature = featureBuilder.build();

			JSONObject jsonObject = toJsonObject(feature);

			Assertions.assertThat(jsonObject.keys())
					.asList()
					.containsOnly("id");

			JSONObject idJson = jsonObject.getJSONObject("id");
			Assertions.assertThat(idJson).isNotNull();
			Assertions.assertThat(idJson.keys())
					.asList()
					.containsOnly("groupId", "artifactId", "version");

			Assertions.assertThat(idJson.getString("groupId")).isEqualTo("g");

			Assertions.assertThat(idJson.getString("artifactId"))
					.isEqualTo("a");

			Assertions.assertThat(idJson.getString("version")).isEqualTo("v");

		}

		@SuppressWarnings("unchecked")
		@Test
		void write_ID_gavtc() throws Exception {
			FeatureBuilder featureBuilder = Features.getBuilderFactory()
					.newFeatureBuilder(ID_GAVTC);
			Feature feature = featureBuilder.build();

			JSONObject json = toJsonObject(feature);

			Assertions.assertThat(json.keys())
					.asList()
					.containsOnly("id");

			JSONObject idJson = json.getJSONObject("id");
			Assertions.assertThat(idJson).isNotNull();
			Assertions.assertThat(idJson.keys())
					.asList()
					.containsOnly("groupId", "artifactId", "version", "type",
							"classifier");

			Assertions.assertThat(idJson.getString("groupId")).isEqualTo("g");

			Assertions.assertThat(idJson.getString("artifactId"))
					.isEqualTo("a");

			Assertions.assertThat(idJson.getString("version")).isEqualTo("v");
			Assertions.assertThat(idJson.getString("type")).isEqualTo("t");
			Assertions.assertThat(idJson.getString("classifier"))
					.isEqualTo("c");

		}

	}

	static JSONObject toJsonObject(Feature feature)
			throws IOException, JSONException {
		Writer writer = new StringWriter();
		Features.writeFeature(feature, writer);
		JSONObject jsonObject = new JSONObject(writer.toString());
		return jsonObject;
	}

}
