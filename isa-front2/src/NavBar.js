import React, { Component } from 'react'
import {Link, withRouter} from "react-router-dom"

class NavBar extends Component{

    componentDidMount(){

    }

    render(){
        return(          
            <nav className="nav-wrapper red darken-3">
                <div className="container">
                    <Link to='/' className="brand-logo">Home</Link>
                    <ul id="nav-mobile" className="right hide-on-med-and-down">
                        <li><Link to="/login">Prijava</Link></li>
                        <li><Link to="/register">Registracija</Link></li>
                    </ul>
                </div>
            </nav>       
        )
    }

}
export default withRouter(NavBar)