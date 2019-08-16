import React, { Component } from 'react';
import axios from 'axios';

class Other extends Component {

    state = {

    }

    componentDidMount() {
        axios.get("http://localhost:8221/aviocompany/getone/")
                .then(res => {
                    
                })
    }

    render() {

        return (
            <div>
                <h3>Uredjivanje prtljaga i brzih rezervacija</h3>
                <h4>Klase</h4>

            </div>
        );
    }
}

export default Other;
