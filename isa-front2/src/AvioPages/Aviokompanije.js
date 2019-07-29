import React, { Component } from 'react'
import { withRouter} from 'react-router-dom';
import ChooseBar from '../ChooseBar';

class Aviokompanije extends Component{

    componentDidMount() {
    }

    render(){
        return(
            <div>
                <ChooseBar/>
                <p>EO ME</p>
            </div>
        )
    }

}
export default withRouter(Aviokompanije)