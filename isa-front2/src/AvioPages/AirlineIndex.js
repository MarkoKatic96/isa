import React, { Component } from 'react'
import { withRouter} from 'react-router-dom';
import FlightsSearch from './User/FlightsSearch';

class Aviokompanije extends Component{

    componentDidMount() {
    }

    render(){
        return(
            <div>
                <FlightsSearch />
            </div>
        )
    }

}
export default withRouter(Aviokompanije)