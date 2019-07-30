import React, { Component } from 'react'
import {Link, withRouter} from "react-router-dom"

class NavBar extends Component{

    odjava = () =>{
        this.props.setToken(sessionStorage.getItem('jwtToken'));
        this.props.setEmail(sessionStorage.getItem('email'));
        this.props.logOut();      
    }

    render(){
        var isLogged = this.props.loggedIn;
        var rola = sessionStorage.getItem('rola');
        var ispis;
        console.log(rola)
        if(rola=="KORISNIK"){
            ispis = <ul id="nav-mobile" className="right hide-on-med-and-down">
                        <li><Link to="/login">Korisnik</Link></li>
                        <li><Link onClick={this.odjava}>Odjava</Link></li>
                    </ul>
        }else if(rola=="ADMIN_AVIO_KOMPANIJE"){
            ispis = <ul id="nav-mobile" className="right hide-on-med-and-down">
                        <li><Link to="/login">Avio</Link></li>
                        <li><Link onClick={this.odjava}>Odjava</Link></li>
                    </ul>
        }else if(rola=="ADMIN_HOTELA"){
            ispis = <ul id="nav-mobile" className="right hide-on-med-and-down">
                        <li><Link to="/login">Izmeni hotel</Link></li>
                        <li><Link to="/login">Sobe</Link></li>
                        <li><Link to="/login">Cenovnik</Link></li>
                        <li><Link to="/login">Izvestaji</Link></li>
                        <li><Link to="/login">Usluge</Link></li>
                        <li><Link to="/login">Profil</Link></li>
                        <li><Link onClick={this.odjava}>Odjava</Link></li>
                    </ul>
        }else if(rola=="ADMIN_RENT_A_CAR"){
            ispis = <ul id="nav-mobile" className="right hide-on-med-and-down">
                        <li><Link to="/login">Rent</Link></li>
                        <li><Link onClick={this.odjava}>Odjava</Link></li>
                    </ul>
        }else if(rola=="MASTER_ADMIN"){
            ispis = <ul id="nav-mobile" className="right hide-on-med-and-down">
                        <li><Link to="/login">Master</Link></li>
                        <li><Link onClick={this.odjava}>Odjava</Link></li>
                    </ul>
        }
        return(          
            <nav className="nav-wrapper red darken-3">
                <div className="container">
                    <Link to='/' className="brand-logo">Home</Link>
                    { isLogged ? (
                        <div>
                            {ispis}
                        </div>
                    ):(
                        <ul id="nav-mobile" className="right hide-on-med-and-down">
                            <li><Link to="/login">Prijava</Link></li>
                            <li><Link to="/register">Registracija</Link></li>
                        </ul>
                    )}
                </div>
            </nav>       
        )
    }

}
export default withRouter(NavBar)