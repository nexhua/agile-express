import React from 'react';

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {clients : []};
        console.log("inside react");
    }

    async componentDidMount() {
        fetch("/clients")
            .then(response => await response.json())
            .then(data => this.setState({
                clients: data
            }));
    }

    render() {
        return (
            <div>
                <table>
                {this.state.clients.map(client => {
                    <tr>
                        <td>client.name</td>
                        <td>client.email</td>
                    </tr>
                })};
                </table>
            </div>
        )
    }
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)