import React, { Component } from 'react';
import { BrowserRouter, Route } from 'react-router-dom';
import NavBar from './NavBar';
import Home from './Home';
import "./card.css"
import Hotels from './HotelPages/Hotels';
import Aviokompanije from './AvioPages/Aviokompanije';
import RentACars from './RentACarPages/RentACars';

class App extends Component {
  render() {
    return (
      <BrowserRouter>
        <div className="App grey lighten-1">
          <NavBar/>
          <Route exact path = '/' render={Home}/>
          <Route path = '/hotels' render={Hotels}/>
          <Route path = '/companies' render={Aviokompanije}/>
          <Route path = '/rent-a-cars' render={RentACars}/>
        </div>
      </BrowserRouter>
    );
  }
}

export default App;
