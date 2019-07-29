import React, { Component } from 'react';
import { BrowserRouter, Route } from 'react-router-dom';
import NavBar from './NavBar';
import Home from './Home';
import "./card.css"
import Hotels from './HotelPages/Hotels';
import Rooms from './HotelPages/Rooms';
import Aviokompanije from './AvioPages/Aviokompanije';
import RentACars from './RentACarPages/RentACars';
import Login from './Login';
import Register from './Register';
import Services from './HotelPages/Services';

class App extends Component {

  state = {
    token:undefined,
    usersEmail:undefined
  }

  
  setToken = (jwt) =>{
    this.setState({
      token:jwt
    })
  }


  setEmail = (email) =>{
    this.setState({
      usersEmail:email
    })
  }

  render() {
    return (
      <BrowserRouter>
        <div className="App grey lighten-1" id="divApp">
          <NavBar token = {this.state.token} setToken = {this.setToken} setEmail = {this.setEmail}/>
          <Route exact path = '/' render={Home}/>
          <Route path = '/hotels' render={Hotels}/>
          <Route path = '/companies' render={Aviokompanije}/>
          <Route path = '/rent-a-cars' render={RentACars}/>
          <Route path = '/login' render={props => <Login setToken = {this.setToken} setEmail = {this.setEmail}/>}/>
          <Route path = '/register' render={Register}/>
          <Route path = '/rooms/:hotelId' render={Rooms}/>
          <Route path = '/services/:serviceId' render={Services}/>
        </div>
      </BrowserRouter>
    );
  }
}

export default App;
