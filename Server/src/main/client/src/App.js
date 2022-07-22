import { BrowserRouter, Route } from 'react-router-dom'; 
import React from 'react';
import routes from './components/PageResolver';


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

export default App;
