package mental.inception.format

import groovy.json.JsonBuilder

class ArrayNodeJson {

	def format(node) {
		return buildJsonArray(createArrayValues(node.value))
	}

	private Collection createArrayValues(nodeValue) {
		return nodeValue.collect { value -> render(value) }
	}

	private render(value) {
		return value.render()
	}

	private buildJsonArray(Collection arrayValues) {
		def jsonBuilder = new JsonBuilder(arrayValues)
		return jsonBuilder.getContent()
	}
}
