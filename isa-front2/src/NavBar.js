import React, { Component } from 'react'
import {Link, withRouter} from "react-router-dom"

class NavBar extends Component{

    odjava = () =>{
        localStorage.setItem('jwtToken', undefined);
        localStorage.setItem('email', undefined);
        localStorage.setItem('rola', undefined);
        localStorage.setItem("isLogged", false);
        this.props.logOut();  
        this.props.history.push("/");    
    }

    render(){
        //var isLogged = this.props.loggedIn;
        var isLogged = localStorage.getItem("isLogged")
        var rola = localStorage.getItem('rola');
        var ispis="";
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
                        <li><Link to="/edit/hotel">Izmeni hotel</Link></li>
                        <li><Link to="/admin/rooms">Sobe</Link></li>
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
                    { (localStorage.getItem("isLogged") && ispis!="") ? (
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