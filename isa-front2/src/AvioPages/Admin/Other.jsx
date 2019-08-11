import React, { Component } from 'react';
import axios from 'axios';

class Other extends Component {

    state = {

    }

    componentDidMount() {
        axios.get("http://localhost:8221/aviocompany/getone/")
                .then(res=>{
                    
                })
    }

    render() {



        return (
            <div>
                <h3>Uredjivanje klasa, dodatnih usluga i podataka o dozvoljenom prtljagu</h3>
                <h4>Klase</h4>

            </div>
        );
    }
}

export default Other;
