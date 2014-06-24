package mental.inception.json;

import static org.junit.Assert.*

import org.junit.Test

import com.mental.inception.json.JsonParser

class JsonDiffValuesTest {

	@Test
	public void twoJsonObjectsEmpty() {
		JsonDiffValues diffValues = createDiffValues('{}', '{}')
		assertTrue(diffValues.areSameType())
		assertTrue(diffValues.areEmpty())
	}

	@Test
	void twoEmptyJsonArrays() {
		JsonDiffValues diffValues = createDiffValues('[]', '[]')
		assertTrue(diffValues.areSameType())
		assertTrue(diffValues.areEmpty())
	}

	@Test
	void oneEmptyObjectWithOneEmptyArray() {
		JsonDiffValues diffValues = createDiffValues('{}', '[]')
		assertFalse(diffValues.areSameType())
		assertTrue(diffValues.areEmpty())
	}

	@Test
	void oneEmptyArrayWithOneEmptyObject() {
		JsonDiffValues diffValues = createDiffValues('[]', '{}')
		assertFalse(diffValues.areSameType())
		assertTrue(diffValues.areEmpty())
	}

	@Test
	void objectWithEmptyObject() {
		JsonDiffValues diffValues = createDiffValues('{"color":"blue"}', '{}')
		assertTrue(diffValues.json2.isEmpty())
		assertTrue(diffValues.areSameType())
		assertEquals([color:"blue"], diffValues.json1.value)
		assertEquals("OBJECT", diffValues.json1.type)
	}

	@Test
	void emptyObjectWithObject() {
		JsonDiffValues diffValues = createDiffValues('{}', '{"color":"green"}')
		assertTrue(diffValues.json1.isEmpty())
		assertTrue(diffValues.areSameType())
		assertEquals([color:"green"], diffValues.json2.value)
		assertEquals("OBJECT", diffValues.json2.type)
	}

	@Test
	void emptyArrayWithArray() {
		JsonDiffValues diffValues = createDiffValues('[]', '["green", "blue"]')
		assertTrue(diffValues.json1.isEmpty())
		assertTrue(diffValues.areSameType())
		assertEquals(["green", "blue"], diffValues.json2.value)
		assertEquals("ARRAY", diffValues.json2.type)
	}

	@Test
	void arrayWithEmptyArray() {
		JsonDiffValues diffValues = createDiffValues('["green", "blue"]', '[]')
		assertTrue(diffValues.json2.isEmpty())
		assertTrue(diffValues.areSameType())
		assertEquals(["green", "blue"], diffValues.json1.value)
		assertEquals("ARRAY", diffValues.json1.type)
	}

	@Test
	void objectWithArray() {
		JsonDiffValues diffValues = createDiffValues('{"color":"blue"}', '["green", "blue"]')
		assertFalse(diffValues.areSameType())
		assertEquals([color:"blue"], diffValues.json1.value)
		assertEquals("OBJECT", diffValues.json1.type)
		assertEquals(["green", "blue"], diffValues.json2.value)
		assertEquals("ARRAY", diffValues.json2.type)
	}

	@Test
	void arrayWithObject() {
		JsonDiffValues diffValues = createDiffValues('["green", "blue"]', '{"color":"blue"}')
		assertFalse(diffValues.areSameType())
		assertEquals(["green", "blue"], diffValues.json1.value)
		assertEquals("ARRAY", diffValues.json1.type)
		assertEquals([color:"blue"], diffValues.json2.value)
		assertEquals("OBJECT", diffValues.json2.type)
	}

	private JsonDiffValues createDiffValues(value1, value2) {
		JsonParser parser = new JsonParser()
		def json1 = parser.parse(value1)
		def json2 = parser.parse(value2)
		return new JsonDiffValues(json1, json2)
	}
}
