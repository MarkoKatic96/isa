import React, { Component } from 'react';
import { BrowserRouter, Route } from 'react-router-dom';
import axios from 'axios'
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
import EditHotel from './HotelPages/Admin/EditHotel';
import HotelRooms from './HotelPages/Admin/HotelRooms';
import AddRoom from "./HotelPages/Admin/AddRoom";

class App extends Component {

  state = {
    token: undefined,
    usersEmail: undefined,
    loggedIn: false
  }

  setToken = (jwt) =>{
    this.setState({
      token:jwt
    })
    console.log(this.state.token);
  }

  setEmail = (email) =>{
    this.setState({
      usersEmail:email
    })
  }

  logIn=()=>{
    this.setState({
      loggedIn: true
    })
    localStorage.setItem("isLogged", true);
  }

  logOut=()=>{
    this.setState({loggedIn: false}); 
    /*console.log(this.state.loggedIn);
    console.log(sessionStorage.getItem('jwtToken'));
    console.log(sessionStorage.getItem('email'));
    console.log(sessionStorage.getItem('rola'));*/
  }

  render() {
    return (
      <BrowserRouter>
        <div className="App grey lighten-1" id="divApp">
          <NavBar token = {this.state.token} setToken = {this.setToken} loggedIn={this.state.loggedIn} setEmail = {this.setEmail} logOut={this.logOut}/>
          <Route exact path = '/' render={Home}/>
          <Route path = '/hotels' render={props => <Hotels loggedIn={this.state.loggedIn}/>}/>
          <Route path = '/companies' render={Aviokompanije}/>
          <Route path = '/rent-a-cars' render={RentACars}/>
          <Route path = '/login' render={props => <Login setToken = {this.setToken} setEmail = {this.setEmail}  logIn={this.logIn}/>}/>
          <Route path = '/register' render={Register}/>
          <Route path = '/rooms/:hotelId' render={Rooms}/>
          <Route path = '/services/:serviceId' render={Services}/>
          <Route path = '/edit/hotel' render={EditHotel}/>
          <Route path = '/admin/rooms' render={HotelRooms}/>
          <Route path = '/admin/add_room/:hotelId' render={AddRoom}/>
        </div>
      </BrowserRouter>
    );
  }
}

export default App;