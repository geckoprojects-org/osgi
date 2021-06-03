package org.osgi.test.cases.feature.junit;

import static org.junit.Assert.assertThrows;
import static org.osgi.test.cases.feature.assertj.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.osgi.service.feature.ID;


public class FeatureIDTest {

	// toString may use type and classifier
	// getType may return an Optional<String>
	// getClassifier may return an Optional<String>

	private static final String	G_A_V			= "g:a:v";
	private static final String	G_A_V_T			= G_A_V + ":t";
	private static final String	G_A_V_T_C		= G_A_V_T + ":c";

	private static final ID		ID_G_A_V		= new ID("g", "a", "v");
	private static final ID		ID_G_A_V_T		= new ID("g", "a", "v", "t",
			null);
	private static final ID		ID_G_A_V_T_C	= new ID("g", "a", "v", "t",
			"c");

	@Test
	void constructor3() throws Exception {

		ID id = new ID("g", "a", "v");
		assertThat(id).hasGroupId("g")
				.hasArtifactId("a")
				.hasVersion("v")
				.hasType(null)
				.hasClassifier(null)
				.isEqualTo(ID_G_A_V)
				.hasToString(G_A_V);// Not explicit specified

	}

	@Test
	void constructor5_gav() throws Exception {

		ID id = new ID("g", "a", "v", null, null);
		assertThat(id).hasGroupId("g")
				.hasArtifactId("a")
				.hasVersion("v")
				.hasType(null)
				.hasClassifier(null)
				.isEqualTo(ID_G_A_V)
				.hasToString(G_A_V);

	}

	@Test
	void constructor5_gavt() throws Exception {

		ID id = new ID("g", "a", "v", "t", null);
		assertThat(id).hasGroupId("g")
				.hasArtifactId("a")
				.hasVersion("v")
				.hasType("t")
				.hasClassifier(null)
				.isEqualTo(ID_G_A_V_T)
				.hasToString(G_A_V_T);// Not explicit specified
	}

	@Test
	void constructor5_gavtc() throws Exception {

		ID id = new ID("g", "a", "v", "t", "c");
		assertThat(id).hasGroupId("g")
				.hasArtifactId("a")
				.hasVersion("v")
				.hasType("t")
				.hasClassifier("c")
				.isEqualTo(ID_G_A_V_T_C)
				.hasToString(G_A_V_T_C);// not explicit specified


	}

	@Test
	void constructor3_shouldFail() throws Exception {

		assertThrows(Throwable.class, () -> new ID(null, null, null));
		assertThrows(Throwable.class, () -> new ID("", "", ""));
		assertThrows(Throwable.class, () -> new ID("g", "a", null));
		assertThrows(Throwable.class, () -> new ID("g", null, "v"));
		assertThrows(Throwable.class, () -> new ID(null, "a", "v"));

	}

	@Test
	void constructor5_shouldFail() throws Exception {

		assertThrows(Throwable.class,
				() -> new ID(null, null, null, null, null));
		assertThrows(Throwable.class, () -> new ID("", "", "", "", ""));
		assertThrows(Throwable.class, () -> new ID("g", "a", null, null, null));
		assertThrows(Throwable.class, () -> new ID("g", "a", "v", null, "c"));

	}

	@Test
	void fromMavenID_shouldFail() throws Exception {
		assertThrows(Throwable.class, () -> ID.fromMavenID(null));
		assertThrows(Throwable.class, () -> ID.fromMavenID(""));
		assertThrows(Throwable.class, () -> ID.fromMavenID(":"));
		assertThrows(Throwable.class, () -> ID.fromMavenID("::"));
		assertThrows(Throwable.class, () -> ID.fromMavenID(":::"));
		assertThrows(Throwable.class, () -> ID.fromMavenID("::::"));

	}

	@Test
	void fromMavenID_Gav() throws Exception {
		ID id = ID.fromMavenID(G_A_V);
		assertThat(id).hasGroupId("g")
				.hasArtifactId("a")
				.hasVersion("v")
				.hasType(null)
				.hasClassifier(null)
				.isEqualTo(ID_G_A_V)
				.hasToString(G_A_V);// Not explicit specified

	}

	@Test
	void fromMavenID_gavt() throws Exception {
		ID id = ID.fromMavenID(G_A_V_T);
		assertThat(id).hasGroupId("g")
				.hasArtifactId("a")
				.hasVersion("v")
				.hasType("t")
				.hasClassifier(null)
				.isEqualTo(ID_G_A_V_T)
				.hasToString(G_A_V_T);// Not explicit specified
	}

	@Test
	void fromMavenID_gavtc() throws Exception {
		ID id = ID.fromMavenID(G_A_V_T_C);
		assertThat(id).hasGroupId("g")
				.hasArtifactId("a")
				.hasVersion("v")
				.hasType("t")
				.hasClassifier("c")
				.isEqualTo(ID_G_A_V_T_C)
				.hasToString(G_A_V_T_C);// Not explicit specified
	}
}
