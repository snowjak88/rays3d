class Rest {
	static getRenderDescriptor(renderId, callback) {
		
		$.getJSON( "/data/render/" + renderId, callback);
	}
	
	static getRenderDescriptorsOnPage(pageNumber, callback) {
	
		$.getJSON( "/data/render/", { page: pageNumber }, callback);
	}
}