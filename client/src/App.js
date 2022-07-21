import React, { Component } from 'react';
import './App.css';
import Home from './components/Home';
import { BrowserRouter, Route, Switch, withRouter } from 'react-router-dom';
import ClientList from './components/ClientList';
import ClientEdit from "./components/ClientEdit";

class App extends Component {
  render() {
    return (
        <BrowserRouter>
          <Switch>
            <Route exact path='/' component={withRouter(Home)}/>
            <Route path='/clients' component={withRouter(ClientList)}/>
            <Route path='/clients/:id' component={withRouter(ClientEdit)}/>
          </Switch>
        </BrowserRouter>
    )
  }
}

export default App;
