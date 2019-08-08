import React, { Component } from 'react';
import Rectangle from 'react-rectangle';
import axios from 'axios';

class PlaneLayout extends Component {
    state = { 
        brojMesta: ""
     }

     componentDidMount() {
        this.state.brojMesta = this.props.brojMesta;
        console.log("BROJ MESTA " + this.state.brojMesta)
     }

    render() {

        return (
            <div>
            <Rectangle aspectRatio={[5, 3]}>
      <div value="ej bre" style={{ background: 'white', width: '50px', height: '50px' }} />
    </Rectangle>
            </div>
        );
    }
}

export default PlaneLayout;
