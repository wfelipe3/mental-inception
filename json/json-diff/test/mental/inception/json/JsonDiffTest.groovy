package mental.inception.json;

import static org.junit.Assert.*
import mental.inception.format.JsonFormatFactory
import mental.inception.json.builder.NodeBuilderFactory

import org.junit.Test


class JsonDiffTest {

	@Test
	void compareWithNullValues() {
		def render = renderCompare(null, null)
		assertEquals([root:[:]], render)
	}

	@Test
	void compareEmptyObjectWithNullValue() {
		def render = renderCompare("{}", null)
		assertEquals([root:[:]], render)
	}

	@Test
	void compareNullValueWithEmptyArray() {
		def render = renderCompare(null, "[]")
		assertEquals([root:[]], render)
	}

	@Test
	public void compareEmtpyObjects() {
		def render = renderCompare('{}', '{}')
		assertEquals([root:[:]], render)
	}

	@Test
	void compareEmptyArrays() {
		def render = renderCompare('[]', '[]')
		assertEquals([root:[]], render)
	}

	@Test
	void compareEmptyArrayWithEmptyObject() {
		def render = renderCompare('[]', '{}')
		assertEquals([root:[old:[], "new":[:], '$type':"MODIFIED"]], render)
	}

	@Test
	void compareEmptyObjectWithEmptyArray() {
		def render = renderCompare('{}', '[]')
		assertEquals([root:[old:[:], "new":[], '$type':"MODIFIED"]], render)
	}

	@Test
	void compareEmptyObjectWithValueObject() {
		def render = renderCompare('{}', '{"color":"blue"}')
		assertEquals([root:[color:["old":null, "new":"blue", '$type':"ADDED"]]], render)
	}

	@Test
	void compareEmptyObjectWithDifferentValueObject() {
		def render = renderCompare('{}', '{"color":"blue", "rgb":[12,34,54]}')
		assertEquals([root:[color:["old":null, "new":"blue", '$type':"ADDED"],
				"rgb":["old":null, "new":[12, 34, 54], '$type':"ADDED"]]], render)
	}

	@Test
	void compareValueObjectWithEmptyObject() {
		def render = renderCompare('{"color":"blue"}', '{}')
		assertEquals([root:[color:["old":"blue", "new":null, '$type':"REMOVED"]]], render)
	}

	@Test
	void compareValueObjectWithArraysAndCompareWithEmptyObject() {
		def render = renderCompare('{"color":"blue", "rgb":[12,34,54]}', '{}')
		assertEquals([root:[color:["old":"blue", "new":null, '$type':"REMOVED"],
				"rgb":["new":null, "old":[12, 34, 54], '$type':"REMOVED"]]], render)
	}

	@Test
	public void compareEqualObjectsWithNullValue() {
		def render = renderCompare('{"color":null}', '{"color":null}')
		assertEquals([root:[color:[old:null, "new":null, '$type':"SAME"]]], render)
	}
	//
	//	@Test
	//	public void compareEqualObjects() {
	//		def render = renderCompare('{"color":"green"}', '{"color":"green"}')
	//		assertEquals('{"root":{"color":{"old":"green","new":"green","$type":"SAME"}}}', render)
	//	}
	//
	//	@Test
	//	public void compareEqualObjectsWithTwoProperties() {
	//		def render = renderCompare('{"color":"green", "position":"0.0"}', '{"color":"green", "position":"0.0"}')
	//		assertEquals('{"root":{"color":{"old":"green","new":"green","$type":"SAME"},"position":{"old":"0.0","new":"0.0","$type":"SAME"}}}', render)
	//	}
	//
	//	@Test
	//	void compareObjectWithDifferentValuesAndNull() {
	//		def render = renderCompare('{"color":null}', '{"color":"red"}')
	//		assertEquals('{"root":{"color":{"old":null,"new":"red","$type":"MODIFIED"}}}', render)
	//	}
	//
	//	@Test
	//	void compareObjectWithDifferentValues() {
	//		def render = renderCompare('{"color":"green"}', '{"color":"red"}')
	//		assertEquals('{"root":{"color":{"old":"green","new":"red","$type":"MODIFIED"}}}', render)
	//	}
	//
	//	@Test
	//	void compareObjectWithEqualInternalObjects() {
	//		def render = renderCompare('{"window":{"color":"green"}}', '{"window":{"color":"green"}}')
	//		assertEquals('{"root":{"window":{"color":{"old":"green","new":"green","$type":"SAME"}}}}', render)
	//	}
	//
	//	@Test
	//	void compareObjectWithDifferentInternalObjects() {
	//		def render = renderCompare('{"window":{"color":"green"}}', '{"window":{"color":"red"}}')
	//		assertEquals('{"root":{"window":{"color":{"old":"green","new":"red","$type":"MODIFIED"}}}}', render)
	//	}
	//
	//	@Test
	//	void compareObjectsWithDifferentValueTypesAndSameKey() {
	//		def render = renderCompare('{"window":{"color":"green"}}', '{"window":{"color":10}}')
	//		assertEquals('{"root":{"window":{"color":{"old":"green","new":10,"$type":"MODIFIED"}}}}', render)
	//	}
	//
	//	@Test
	//	void compareObjectsWithSameKeyAndDifferentValueObjects() {
	//		def render = renderCompare('{"window":{"color":"green"}}', '{"window":{"color":{"r":10,"g":20,"b":89}}}')
	//		assertEquals('{"root":{"window":{"color":{"old":"green","new":{"g":20,"b":89,"r":10},"$type":"MODIFIED"}}}}', render)
	//	}
	//
	//	@Test
	//	void compareTwoEqualJsonObjectsWithComplexValue() {
	//		def render = renderCompare('{"color":"green", "position":{"x":0, "y":0}}', '{"color":"green", "position":{"x":0, "y":0}}')
	//		assertEquals('{"root":{"color":{"old":"green","new":"green","$type":"SAME"},"position":{"x":{"old":0,"new":0,"$type":"SAME"},"y":{"old":0,"new":0,"$type":"SAME"}}}}', render)
	//	}
	//
	//	@Test
	//	void compareJsonObjectsWithComplexValueAndDifferentTypes() {
	//		def render = renderCompare('{"color":"green", "position":{"x":0, "y":0}}', '{"color":"green", "position":"0.0"}')
	//		assertEquals('{"root":{"color":{"old":"green","new":"green","$type":"SAME"},"position":{"old":{"y":0,"x":0},"new":"0.0","$type":"MODIFIED"}}}', render)
	//	}
	//
	//	@Test
	//	public void compareObjectsWithDeletedKey() {
	//		def render = renderCompare('{"color":"green","position":"0.0"}', '{"position":"0.0"}')
	//		assertEquals('{"root":{"color":{"old":"green","new":null,"$type":"DELETED"},"position":{"old":"0.0","new":"0.0","$type":"SAME"}}}', render)
	//	}
	//
	//	@Test
	//	public void compareObjectsWithAddedKey() {
	//		def render = renderCompare('{"position":"0.0"}', '{"color":"green","position":"0.0"}')
	//		assertEquals('{"root":{"color":{"old":null,"new":"green","$type":"ADDED"},"position":{"old":"0.0","new":"0.0","$type":"SAME"}}}', render)
	//	}
	//
	//	@Test
	//	public void compareObjectsWithSameAddedDeletedKeys() {
	//		def render = renderCompare('{"position":"0.0","image":"/disk/image.png"}', '{"color":"green","position":"1.0"}')
	//		assertEquals('{"root":{"color":{"old":null,"new":"green","$type":"ADDED"},"image":{"old":"/disk/image.png","new":null,"$type":"DELETED"},"position":{"old":"0.0","new":"1.0","$type":"MODIFIED"}}}', render)
	//	}

	private def renderCompare(json1, json2) {
		return diffJson(json1, json2).render()
	}

	private def diffJson(String json1, String json2) {
		def nodeBuilderFactory = new NodeBuilderFactory(new JsonFormatFactory())
		def diff = new JsonDiff(nodeBuilderFactory)
		return diff.compareDiff(json1, json2)
	}
}
