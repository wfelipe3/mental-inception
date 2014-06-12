package mental.inception.json;

import static org.junit.Assert.*
import groovy.json.JsonBuilder;
import mental.inception.format.JsonFormatFactory;

import org.junit.Test


class JsonDiffTest {

	@Test
	public void compareEmtpyObjects() {
		def render = renderCompare('{}', '{}')
		assertEquals('{"root":{}}', render)
	}

	@Test
	public void compareEqualObjectsWithNullValue() {
		def render = renderCompare('{"color":null}', '{"color":null}')
		assertEquals('{"root":{"color":{"old":null,"new":null,"$type":"SAME"}}}', render)
	}
	
	@Test
	public void compareEqualObjects() {
		def render = renderCompare('{"color":"green"}', '{"color":"green"}')
		assertEquals('{"root":{"color":{"old":"green","new":"green","$type":"SAME"}}}', render)
	}

	@Test
	public void compareEqualObjectsWithTwoProperties() {
		def render = renderCompare('{"color":"green", "position":"0.0"}', '{"color":"green", "position":"0.0"}')
		assertEquals('{"root":{"color":{"old":"green","new":"green","$type":"SAME"},"position":{"old":"0.0","new":"0.0","$type":"SAME"}}}', render)
	}

//	@Test
//	void compareObjectWithDifferentValuesAndNull() {
//		def render = renderCompare('{"color":null}', '{"color":"red"}')
//		assertEquals('{"root":{"color":{"old":null,"new":"red","$type":"MODIFIED"}}}', render)
//	}
	
	@Test
	void compareObjectWithDifferentValues() {
		def render = renderCompare('{"color":"green"}', '{"color":"red"}')
		assertEquals('{"root":{"color":{"old":"green","new":"red","$type":"MODIFIED"}}}', render)
	}

	@Test
	void compareObjectWithEqualInternalObjects() {
		def render = renderCompare('{"window":{"color":"green"}}', '{"window":{"color":"green"}}')
		assertEquals('{"root":{"window":{"color":{"old":"green","new":"green","$type":"SAME"}}}}', render)
	}

	@Test
	void compareObjectWithDifferentInternalObjects() {
		def render = renderCompare('{"window":{"color":"green"}}', '{"window":{"color":"red"}}')
		assertEquals('{"root":{"window":{"color":{"old":"green","new":"red","$type":"MODIFIED"}}}}', render)
	}

	@Test
	void compareObjectsWithDifferentValueTypesAndSameKey() {
		def render = renderCompare('{"window":{"color":"green"}}', '{"window":{"color":10}}')
		assertEquals('{"root":{"window":{"color":{"old":"green","new":10,"$type":"MODIFIED"}}}}', render)
	}

	@Test
	void compareObjectsWithSameKeyAndDifferentValueObjects() {
		def render = renderCompare('{"window":{"color":"green"}}', '{"window":{"color":{"r":10,"g":20,"b":89}}}')
		assertEquals('{"root":{"window":{"color":{"old":"green","new":{"g":20,"b":89,"r":10},"$type":"MODIFIED"}}}}', render)
	}

	@Test
	void compareTwoEqualJsonObjectsWithComplexValue() {
		def render = renderCompare('{"color":"green", "position":{"x":0, "y":0}}', '{"color":"green", "position":{"x":0, "y":0}}')
		assertEquals('{"root":{"color":{"old":"green","new":"green","$type":"SAME"},"position":{"x":{"old":0,"new":0,"$type":"SAME"},"y":{"old":0,"new":0,"$type":"SAME"}}}}', render)
	}

	@Test
	void compareJsonObjectsWithComplexValueAndDifferentTypes() {
		def render = renderCompare('{"color":"green", "position":{"x":0, "y":0}}', '{"color":"green", "position":"0.0"}')
		assertEquals('{"root":{"color":{"old":"green","new":"green","$type":"SAME"},"position":{"old":{"y":0,"x":0},"new":"0.0","$type":"MODIFIED"}}}', render)
	}

	@Test
	public void compareObjectsWithDeletedKey() {
		def render = renderCompare('{"color":"green","position":"0.0"}', '{"position":"0.0"}')
		assertEquals('{"root":{"color":{"old":"green","new":null,"$type":"DELETED"},"position":{"old":"0.0","new":"0.0","$type":"SAME"}}}', render)
	}

	@Test
	public void compareObjectsWithAddedKey() {
		def render = renderCompare('{"position":"0.0"}', '{"color":"green","position":"0.0"}')
		assertEquals('{"root":{"color":{"old":null,"new":"green","$type":"ADDED"},"position":{"old":"0.0","new":"0.0","$type":"SAME"}}}', render)
	}

	private def renderCompare(json1, json2) {
		def json = diffJson(json1, json2).render()
		return new JsonBuilder(json).toString()
	}

	private def diffJson(String json1, String json2) {
		def treeBuilder = new DiffTreeBuilder(new JsonFormatFactory())
		def diff = new JsonDiff(treeBuilder, json1, json2)
		return diff.compareDiff()
	}
}
