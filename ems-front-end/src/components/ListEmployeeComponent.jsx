import React, {useState} from 'react'  //rafce
import { useEffect } from 'react'
import { deleteEmployee, listEmployees } from '../services/EmployeeService'
import { useNavigate } from 'react-router-dom'

const ListEmployeeComponent = () => {

    const [employees, setEmployees] = useState([])
    //useState() fonksiyonu, bileşenin durum değişkenlerini oluşturmak ve güncellemek için kullanılır
    //başlangıçta belirtilen boş bir dizi ile employees adında bir durum değişkeni oluşturur.
    //setEmployees fonksiyonu ise employees durum değişkenini güncellemek için kullanılır.

    const navigator = useNavigate();

    // useEffect() hook'u, bileşenin render edildikten sonra çalışacak yan etkileri yönetmemizi sağlar.
    useEffect(() => {
        getAllEmployees();

    }, []) // Boş bir dizi, useEffect'in yalnızca bileşen yüklendiğinde çalışmasını sağlar.
    
    function getAllEmployees(){
        // listEmployees() fonksiyonunu çağırıyoruz ve bu fonksiyon bir Promise döndürüyor.
        listEmployees().then((response) => {
        // Promise çözüldüğünde, sunucudan gelen veriyi "employees" durum değişkenine atıyoruz.
            setEmployees(response.data);
        }).catch(error => {
        // Promise reddedildiğinde, oluşan hatayı konsola yazdırıyoruz.
            console.error(error);
        })
    }

    function addNewEmployee(){
        navigator('/add-employee')
    }

    function updateEmployee(id){
        navigator(`/edit-employee/${id}`)
    }

    function removeEmployee(id){
        console.log(id);
        deleteEmployee(id).then((response) =>{
            getAllEmployees();
        }).catch(error => {
            console.error(error);
        })
    }

  return (
    <div className='container'>
        <h2 className = 'text-center'> List of Employees</h2>
        <button className ='btn btn-primary mb-2' onClick={addNewEmployee}>Add Employee</button>
        <table className='table table-striped table-dark table-hover'>
            <thead>
                <tr> 
                    <th>Emplyee Id</th>
                    <th>Employee First Name</th>
                    <th>Employee Last Name</th>
                    <th>Emplyee Email Id</th>
                    <th>Actions</th>

                </tr>
            </thead>
            <tbody>
                {
                   employees.map(employee =>
                    <tr key= {employee.id}>
                        <td>{employee.id}</td>
                        <td>{employee.firstName}</td>
                        <td>{employee.lastName}</td>
                        <td>{employee.email}</td>
                        <td>
                            <button className= 'btn btn-info' onClick= {() => updateEmployee(employee.id)}>Update</button>
                            <button className='btn btn-danger' onClick={() => removeEmployee(employee.id)}
                                style = {{marginLeft: '10px'}}
                            >Delete</button>

                        </td>

                    </tr>) 
                }
            </tbody>
        </table>
    </div>
  )
}

export default ListEmployeeComponent