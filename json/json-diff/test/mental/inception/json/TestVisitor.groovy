package mental.inception.json

class TestVisitor {

	def value

	void navigate(node) {
		value = "{${node.key}:${node.value.value}}"
	}
}
