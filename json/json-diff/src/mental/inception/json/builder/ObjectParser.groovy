package mental.inception.json.builder

import groovy.json.JsonSlurper;

class ObjectParser {

	def parse(json) {
		return json
	}

	def getEmptyObject() {
		return [:]
	}

	def getEmptyArray() {
		return []
	}

	def isObject(value) {
		return value instanceof Map
	}
}
