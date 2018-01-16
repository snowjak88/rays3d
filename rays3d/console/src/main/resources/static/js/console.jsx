/*
 * 
 */

class RenderDescriptorEntry extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			id: props.id,
			descriptor: props.descriptor
		}
		
		let thisComponent = this;
		
		if(props.descriptor == null)
			refreshDescriptor();
	}
	
	refreshDescriptor() {
		let thisComponent = this;
		
		Rest.getRenderDescriptor(
				this.state.id,
				function(refreshedDescriptor) {
					thisComponent.setState( {
								descriptor: refreshedDescriptor
							} );
				});
	}
	
	render() {
		
		let rd = this.state.descriptor;

		return (
			<tr className="">
				<td>{rd.id}</td>
				<td>{rd.created}</td>
				<td>{rd.filmWidth}x{rd.filmHeight}</td>
				<td>{rd.samplerName}</td>
				<td>{rd.samplesPerPixel}</td>
				<td>{rd.integratorName}</td>
				<td>{rd.extraIntegratorConfig}</td>
				<td>{rd.renderingStatus}</td>
				<td></td>
			</tr>
		);
	}
}

class RenderDescriptorList extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			page: ( props.page == null ? 0 : props.page ),
			descriptors: []
		}
		
		this.refreshDescriptors();
	}
	
	
	
	pageLeft() {
		
		if(this.state.page > 0) {
			
			let newPage = this.state.page - 1;
			let thisComponent = this;
			
			Rest.getRenderDescriptorsOnPage(
					newPage,
					function(newDescriptors) {
						thisComponent.setState({
									page: newPage,
									descriptors: newDescriptors
								});
					});
		}
	}
	
	pageRight() {
		let newPage = this.state.page + 1;
		let thisComponent = this;
		
		Rest.getRenderDescriptorsOnPage(
				newPage,
				function(newDescriptors) {
					thisComponent.setState(
							{
								page: newPage,
								descriptors: newDescriptors
							});
				});
	}
	
	refreshDescriptors() {
		let thisComponent = this;
		Rest.getRenderDescriptorsOnPage(
				this.state.page,
				function(newDescriptors) {
					thisComponent.setState( {
								descriptors: newDescriptors
							});
				});
	}
	
	render() {
		
		let descriptorComponents = $.map(
				this.state.descriptors,
				function(val, i) {
					return ( <RenderDescriptorEntry id={val.id} key={val.id} descriptor={val}/> );
				});
		
		return (
			<table className="table">
				<thead>
					<tr>
						<td><button onClick={() => this.pageLeft()} className="btn btn-outline-primary btn-sm">&lt;==</button></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td><button onClick={() => this.pageRight()} className="btn btn-outline-primary btn-sm">==&gt;</button></td>
					</tr>
					<tr>
						<th>ID</th>
						<th>Created</th>
						<th>Film</th>
						<th>Sampler</th>
						<th>spp</th>
						<th>Integrator</th>
						<th>Integrator Config</th>
						<th>Render Status</th>
						<th><button onClick={() => this.refreshDescriptors()} className="btn btn-outline-info btn-sm" role="button">Refresh</button></th>
					</tr>
				</thead>
				<tbody>
					{descriptorComponents}
				</tbody>
			</table>
		);
	}
}

/*
 * 
 */

ReactDOM.render(<RenderDescriptorList />, document.getElementById("root"));