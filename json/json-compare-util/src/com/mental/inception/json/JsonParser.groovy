package com.mental.inception.json

import groovy.json.JsonSlurper;

class JsonParser {

	JsonSlurper parser

	JsonParser() {
		parser = new JsonSlurper()
	}

	def parse(json) {
		return parser.parseText(json)
	}

	def getEmptyObject() {
		return "{}"
	}

	def getEmptyArray() {
		return "[]"
	}

	def isObject(value) {
		return value.charAt(0) == '{'
	}
}
