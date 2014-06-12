package com.mental.inception.json;

import static org.junit.Assert.*;

import org.junit.Test;

class JsonParserTest {

	@Test
	public void parseEmptyJson() {
		def emptyJson = parse('{}')
		assertTrue emptyJson.isEmpty()
	}

	@Test
	public void parserSimpleJson() {
		def json = parse('{"value":"test"}')
		assertEquals 'test', json.value
	}

	private parse(json) {
		JsonParser parser = new JsonParser()
		return parser.parse(json)
	}
}
