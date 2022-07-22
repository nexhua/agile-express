import logo from './logo.svg';
import './App.css';
import Hello from './pages/Hello';
import User from './pages/User';
import { BrowserRouter, Route } from 'react-router-dom'; 
import React from 'react';


class App extends React.Component {
  render() {
    const routeComponents = routes.map(({path, component}, key) => <Route exact path={path} component={component} key={key} />);
    <p>{document.location.pathname}</p>
    return (
      <BrowserRouter>
        <div>
        {routeComponents}
        </div>
      </BrowserRouter>
    );
  }
}

const routes = [ {
  path: '/hello',
  component: Hello
} , {
  path: '/user',
  component: User
} , {
  path: '/dashboard',
  component: User
}]; 

export default App;
