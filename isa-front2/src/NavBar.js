import React, { Component } from 'react'
import {Link, withRouter} from "react-router-dom"

class NavBar extends Component{

    state={
        token:sessionStorage.getItem('jwtToken')
    }

    componentDidMount(){
        this.setState({
            token: sessionStorage.getItem('jwtToken')
        })
    }

    odjava = () =>{
        sessionStorage.setItem('jwtToken', undefined);
        sessionStorage.setItem('email', undefined)
        this.props.setToken(sessionStorage.getItem('jwtToken'));
        this.props.setEmail(sessionStorage.getItem('email'));
        this.setState({
            token: sessionStorage.getItem('jwtToken')
        })
        this.props.history.push("/");
    }

    render(){
        var isLogged = sessionStorage.getItem('jwtToken');
        return(          
            <nav className="nav-wrapper red darken-3">
                <div className="container">
                    <Link to='/' className="brand-logo">Home</Link>
                    { isLogged !== undefined ? (
                        <ul id="nav-mobile" className="right hide-on-med-and-down">
                            <li><Link to="/login">Prijava</Link></li>
                            <li><Link to="/register">Registracija</Link></li>
                        </ul>
                    ):(
                        <ul id="nav-mobile" className="right hide-on-med-and-down">
                            <li><Link to="/login">Korisnik</Link></li>
                            <li><Link onClick={this.odjava}>Odjava</Link></li>
                        </ul>
                    )}
                </div>
            </nav>       
        )
    }

}
export default withRouter(NavBar)