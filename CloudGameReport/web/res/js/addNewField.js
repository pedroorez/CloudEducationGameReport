/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var i = 2;
function addMoreField(){
    
    var html =  '<hr>'+
                '<table>'+
                '<tr><td>Field Name: </td> <td><input name="fieldName_'+i+'" type="text"><br></td></tr>'+
                '<tr><td>Field Identificator: </td> <td><input name="fieldIdentificator_'+i+'" type="text"><br></td></tr>'+
                '<tr >'+
                '    <td>Display Type: </td>'+
                '    <td align="center">    '+
                '<select class="selectpicker" name="displaytype_'+i+'">'+
                '        <option value="SUM">Sum</option>'+
                '        <option value="LIST">List</option>'+
                '    </select>'+
                '    </td></tr>'+
                ' </table>';
    $( "#Fields" ).append( html );
    $( '#amountOfFields' ).val(i);
    i++;
    
    
}