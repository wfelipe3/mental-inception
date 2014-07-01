package mental.inception.json

import com.mental.inception.json.JsonParser

class JsonDiffParser {

	private def parser
	def value1
	def value2

	JsonDiffParser(parser, value1, value2) {
		this.parser = parser
		this.value1 = value1
		this.value2 = value2
	}

	def parseKeys() {
		convertNullValuesToDefaultType()
		return parseValues()
	}

	private convertNullValuesToDefaultType() {
		if (isNull(value1) && isNull(value2))
			convertValuesToEmptyObject()
		else if (isNull(value1))
			value1 = getEmptyValueWithTypeOf(value2)
		else if (isNull(value2))
			value2 = getEmptyValueWithTypeOf(value1)
	}

	private isNull(value) {
		return value == null
	}

	private convertValuesToEmptyObject() {
		value1 = getEmptyObject()
		value2 = getEmptyObject()
	}

	private def getEmptyValueWithTypeOf(value) {
		if (isObject(value))
			return getEmptyObject()
		else
			return getEmptyArray()
	}

	private getEmptyObject() {
		return parser.getEmptyObject()
	}

	private getEmptyArray() {
		return parser.getEmptyArray()
	}

	private isObject(value) {
		return parser.isObject(value)
	}

	private parseValues() {
		def json1 = parser.parse(value1)
		def json2 = parser.parse(value2)
		return new JsonDiffValues(json1, json2)
	}
}
