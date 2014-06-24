package mental.inception.json

import com.mental.inception.json.JsonParser

class JsonDiffParser {

	private static final String EMPTY_JSON_OBJECT = "{}"
	private static final String EMPTY_JSON_ARRAY = "[]"

	def value1
	def value2

	def parseKeys() {
		convertNullValuesToDefaultType()
		return parseValues()
	}

	private convertNullValuesToDefaultType() {
		if (isNull(value1) && isNull(value2)) {
			convertValuesToEmptyJsonObject()
		} else if (isNull(value1)) {
			value1 = getEmptyJsonValue(value2)
		} else if (isNull(value2)) {
			value2 = getEmptyJsonValue(value1)
		}
	}

	private isNull(value) {
		return value == null
	}

	private convertValuesToEmptyJsonObject() {
		value1 = EMPTY_JSON_OBJECT
		value2 = EMPTY_JSON_OBJECT
	}

	private def getEmptyJsonValue(value) {
		if (isJsonObject(value)) {
			return EMPTY_JSON_OBJECT
		} else {
			return EMPTY_JSON_ARRAY
		}
	}

	private isJsonObject(value) {
		return value.charAt(0) == '{'
	}

	private parseValues() {
		JsonParser parser = new JsonParser()
		def json1 = parser.parse(value1)
		def json2 = parser.parse(value2)
		return new JsonDiffValues(json1, json2)
	}
}
