import React, { Component } from 'react'
import { withRouter} from 'react-router-dom';
import UserHome from './User/UserHome';

class Aviokompanije extends Component{

    componentDidMount() {
    }

    render(){
        return(
            <div>
                <UserHome />
            </div>
        )
    }

}
export default withRouter(Aviokompanije)