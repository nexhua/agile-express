import React from "react";


class DetailList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            project: this.props.project,
            row: this.props.row
        }
    }

    render() {
        return(
            <tr onClick={this.props.clickFunction}>
                <th scope="row">{this.state.row + 1}</th>
                <td>{this.state.project.projectName}</td>
                <td>{this.state.project.startDate}</td>
                <td>{this.state.project.endDate}</td>
            </tr>
        );
    }
    
}

export default DetailList