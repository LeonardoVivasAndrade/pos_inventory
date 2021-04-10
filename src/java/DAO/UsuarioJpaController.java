/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Cliente;
import java.util.ArrayList;
import java.util.List;
import DTO.Compra;
import DTO.Venta;
import DTO.Departamento;
import DTO.Cajachica;
import DTO.Proveedor;
import DTO.Dventa;
import DTO.Dcompra;
import DTO.Stock;
import DTO.Inventario;
import DTO.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Leonardo
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getClienteList() == null) {
            usuario.setClienteList(new ArrayList<Cliente>());
        }
        if (usuario.getCompraList() == null) {
            usuario.setCompraList(new ArrayList<Compra>());
        }
        if (usuario.getVentaList() == null) {
            usuario.setVentaList(new ArrayList<Venta>());
        }
        if (usuario.getDepartamentoList() == null) {
            usuario.setDepartamentoList(new ArrayList<Departamento>());
        }
        if (usuario.getCajachicaList() == null) {
            usuario.setCajachicaList(new ArrayList<Cajachica>());
        }
        if (usuario.getProveedorList() == null) {
            usuario.setProveedorList(new ArrayList<Proveedor>());
        }
        if (usuario.getDventaList() == null) {
            usuario.setDventaList(new ArrayList<Dventa>());
        }
        if (usuario.getDcompraList() == null) {
            usuario.setDcompraList(new ArrayList<Dcompra>());
        }
        if (usuario.getStockList() == null) {
            usuario.setStockList(new ArrayList<Stock>());
        }
        if (usuario.getInventarioList() == null) {
            usuario.setInventarioList(new ArrayList<Inventario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : usuario.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getClId());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            usuario.setClienteList(attachedClienteList);
            List<Compra> attachedCompraList = new ArrayList<Compra>();
            for (Compra compraListCompraToAttach : usuario.getCompraList()) {
                compraListCompraToAttach = em.getReference(compraListCompraToAttach.getClass(), compraListCompraToAttach.getCoId());
                attachedCompraList.add(compraListCompraToAttach);
            }
            usuario.setCompraList(attachedCompraList);
            List<Venta> attachedVentaList = new ArrayList<Venta>();
            for (Venta ventaListVentaToAttach : usuario.getVentaList()) {
                ventaListVentaToAttach = em.getReference(ventaListVentaToAttach.getClass(), ventaListVentaToAttach.getVeId());
                attachedVentaList.add(ventaListVentaToAttach);
            }
            usuario.setVentaList(attachedVentaList);
            List<Departamento> attachedDepartamentoList = new ArrayList<Departamento>();
            for (Departamento departamentoListDepartamentoToAttach : usuario.getDepartamentoList()) {
                departamentoListDepartamentoToAttach = em.getReference(departamentoListDepartamentoToAttach.getClass(), departamentoListDepartamentoToAttach.getDpId());
                attachedDepartamentoList.add(departamentoListDepartamentoToAttach);
            }
            usuario.setDepartamentoList(attachedDepartamentoList);
            List<Cajachica> attachedCajachicaList = new ArrayList<Cajachica>();
            for (Cajachica cajachicaListCajachicaToAttach : usuario.getCajachicaList()) {
                cajachicaListCajachicaToAttach = em.getReference(cajachicaListCajachicaToAttach.getClass(), cajachicaListCajachicaToAttach.getCaId());
                attachedCajachicaList.add(cajachicaListCajachicaToAttach);
            }
            usuario.setCajachicaList(attachedCajachicaList);
            List<Proveedor> attachedProveedorList = new ArrayList<Proveedor>();
            for (Proveedor proveedorListProveedorToAttach : usuario.getProveedorList()) {
                proveedorListProveedorToAttach = em.getReference(proveedorListProveedorToAttach.getClass(), proveedorListProveedorToAttach.getPrId());
                attachedProveedorList.add(proveedorListProveedorToAttach);
            }
            usuario.setProveedorList(attachedProveedorList);
            List<Dventa> attachedDventaList = new ArrayList<Dventa>();
            for (Dventa dventaListDventaToAttach : usuario.getDventaList()) {
                dventaListDventaToAttach = em.getReference(dventaListDventaToAttach.getClass(), dventaListDventaToAttach.getDveId());
                attachedDventaList.add(dventaListDventaToAttach);
            }
            usuario.setDventaList(attachedDventaList);
            List<Dcompra> attachedDcompraList = new ArrayList<Dcompra>();
            for (Dcompra dcompraListDcompraToAttach : usuario.getDcompraList()) {
                dcompraListDcompraToAttach = em.getReference(dcompraListDcompraToAttach.getClass(), dcompraListDcompraToAttach.getDcoId());
                attachedDcompraList.add(dcompraListDcompraToAttach);
            }
            usuario.setDcompraList(attachedDcompraList);
            List<Stock> attachedStockList = new ArrayList<Stock>();
            for (Stock stockListStockToAttach : usuario.getStockList()) {
                stockListStockToAttach = em.getReference(stockListStockToAttach.getClass(), stockListStockToAttach.getStIdinventario());
                attachedStockList.add(stockListStockToAttach);
            }
            usuario.setStockList(attachedStockList);
            List<Inventario> attachedInventarioList = new ArrayList<Inventario>();
            for (Inventario inventarioListInventarioToAttach : usuario.getInventarioList()) {
                inventarioListInventarioToAttach = em.getReference(inventarioListInventarioToAttach.getClass(), inventarioListInventarioToAttach.getInId());
                attachedInventarioList.add(inventarioListInventarioToAttach);
            }
            usuario.setInventarioList(attachedInventarioList);
            em.persist(usuario);
            for (Cliente clienteListCliente : usuario.getClienteList()) {
                Usuario oldClIduserOfClienteListCliente = clienteListCliente.getClIduser();
                clienteListCliente.setClIduser(usuario);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldClIduserOfClienteListCliente != null) {
                    oldClIduserOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldClIduserOfClienteListCliente = em.merge(oldClIduserOfClienteListCliente);
                }
            }
            for (Compra compraListCompra : usuario.getCompraList()) {
                Usuario oldCoIduserOfCompraListCompra = compraListCompra.getCoIduser();
                compraListCompra.setCoIduser(usuario);
                compraListCompra = em.merge(compraListCompra);
                if (oldCoIduserOfCompraListCompra != null) {
                    oldCoIduserOfCompraListCompra.getCompraList().remove(compraListCompra);
                    oldCoIduserOfCompraListCompra = em.merge(oldCoIduserOfCompraListCompra);
                }
            }
            for (Venta ventaListVenta : usuario.getVentaList()) {
                Usuario oldVeIduserOfVentaListVenta = ventaListVenta.getVeIduser();
                ventaListVenta.setVeIduser(usuario);
                ventaListVenta = em.merge(ventaListVenta);
                if (oldVeIduserOfVentaListVenta != null) {
                    oldVeIduserOfVentaListVenta.getVentaList().remove(ventaListVenta);
                    oldVeIduserOfVentaListVenta = em.merge(oldVeIduserOfVentaListVenta);
                }
            }
            for (Departamento departamentoListDepartamento : usuario.getDepartamentoList()) {
                Usuario oldDpIduserOfDepartamentoListDepartamento = departamentoListDepartamento.getDpIduser();
                departamentoListDepartamento.setDpIduser(usuario);
                departamentoListDepartamento = em.merge(departamentoListDepartamento);
                if (oldDpIduserOfDepartamentoListDepartamento != null) {
                    oldDpIduserOfDepartamentoListDepartamento.getDepartamentoList().remove(departamentoListDepartamento);
                    oldDpIduserOfDepartamentoListDepartamento = em.merge(oldDpIduserOfDepartamentoListDepartamento);
                }
            }
            for (Cajachica cajachicaListCajachica : usuario.getCajachicaList()) {
                Usuario oldCaIduserOfCajachicaListCajachica = cajachicaListCajachica.getCaIduser();
                cajachicaListCajachica.setCaIduser(usuario);
                cajachicaListCajachica = em.merge(cajachicaListCajachica);
                if (oldCaIduserOfCajachicaListCajachica != null) {
                    oldCaIduserOfCajachicaListCajachica.getCajachicaList().remove(cajachicaListCajachica);
                    oldCaIduserOfCajachicaListCajachica = em.merge(oldCaIduserOfCajachicaListCajachica);
                }
            }
            for (Proveedor proveedorListProveedor : usuario.getProveedorList()) {
                Usuario oldPrIduserOfProveedorListProveedor = proveedorListProveedor.getPrIduser();
                proveedorListProveedor.setPrIduser(usuario);
                proveedorListProveedor = em.merge(proveedorListProveedor);
                if (oldPrIduserOfProveedorListProveedor != null) {
                    oldPrIduserOfProveedorListProveedor.getProveedorList().remove(proveedorListProveedor);
                    oldPrIduserOfProveedorListProveedor = em.merge(oldPrIduserOfProveedorListProveedor);
                }
            }
            for (Dventa dventaListDventa : usuario.getDventaList()) {
                Usuario oldDveIduserOfDventaListDventa = dventaListDventa.getDveIduser();
                dventaListDventa.setDveIduser(usuario);
                dventaListDventa = em.merge(dventaListDventa);
                if (oldDveIduserOfDventaListDventa != null) {
                    oldDveIduserOfDventaListDventa.getDventaList().remove(dventaListDventa);
                    oldDveIduserOfDventaListDventa = em.merge(oldDveIduserOfDventaListDventa);
                }
            }
            for (Dcompra dcompraListDcompra : usuario.getDcompraList()) {
                Usuario oldDcoIduserOfDcompraListDcompra = dcompraListDcompra.getDcoIduser();
                dcompraListDcompra.setDcoIduser(usuario);
                dcompraListDcompra = em.merge(dcompraListDcompra);
                if (oldDcoIduserOfDcompraListDcompra != null) {
                    oldDcoIduserOfDcompraListDcompra.getDcompraList().remove(dcompraListDcompra);
                    oldDcoIduserOfDcompraListDcompra = em.merge(oldDcoIduserOfDcompraListDcompra);
                }
            }
            for (Stock stockListStock : usuario.getStockList()) {
                Usuario oldStIduserOfStockListStock = stockListStock.getStIduser();
                stockListStock.setStIduser(usuario);
                stockListStock = em.merge(stockListStock);
                if (oldStIduserOfStockListStock != null) {
                    oldStIduserOfStockListStock.getStockList().remove(stockListStock);
                    oldStIduserOfStockListStock = em.merge(oldStIduserOfStockListStock);
                }
            }
            for (Inventario inventarioListInventario : usuario.getInventarioList()) {
                Usuario oldInIduserOfInventarioListInventario = inventarioListInventario.getInIduser();
                inventarioListInventario.setInIduser(usuario);
                inventarioListInventario = em.merge(inventarioListInventario);
                if (oldInIduserOfInventarioListInventario != null) {
                    oldInIduserOfInventarioListInventario.getInventarioList().remove(inventarioListInventario);
                    oldInIduserOfInventarioListInventario = em.merge(oldInIduserOfInventarioListInventario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getUsId());
            List<Cliente> clienteListOld = persistentUsuario.getClienteList();
            List<Cliente> clienteListNew = usuario.getClienteList();
            List<Compra> compraListOld = persistentUsuario.getCompraList();
            List<Compra> compraListNew = usuario.getCompraList();
            List<Venta> ventaListOld = persistentUsuario.getVentaList();
            List<Venta> ventaListNew = usuario.getVentaList();
            List<Departamento> departamentoListOld = persistentUsuario.getDepartamentoList();
            List<Departamento> departamentoListNew = usuario.getDepartamentoList();
            List<Cajachica> cajachicaListOld = persistentUsuario.getCajachicaList();
            List<Cajachica> cajachicaListNew = usuario.getCajachicaList();
            List<Proveedor> proveedorListOld = persistentUsuario.getProveedorList();
            List<Proveedor> proveedorListNew = usuario.getProveedorList();
            List<Dventa> dventaListOld = persistentUsuario.getDventaList();
            List<Dventa> dventaListNew = usuario.getDventaList();
            List<Dcompra> dcompraListOld = persistentUsuario.getDcompraList();
            List<Dcompra> dcompraListNew = usuario.getDcompraList();
            List<Stock> stockListOld = persistentUsuario.getStockList();
            List<Stock> stockListNew = usuario.getStockList();
            List<Inventario> inventarioListOld = persistentUsuario.getInventarioList();
            List<Inventario> inventarioListNew = usuario.getInventarioList();
            List<String> illegalOrphanMessages = null;
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteListOldCliente + " since its clIduser field is not nullable.");
                }
            }
            for (Compra compraListOldCompra : compraListOld) {
                if (!compraListNew.contains(compraListOldCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Compra " + compraListOldCompra + " since its coIduser field is not nullable.");
                }
            }
            for (Venta ventaListOldVenta : ventaListOld) {
                if (!ventaListNew.contains(ventaListOldVenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Venta " + ventaListOldVenta + " since its veIduser field is not nullable.");
                }
            }
            for (Departamento departamentoListOldDepartamento : departamentoListOld) {
                if (!departamentoListNew.contains(departamentoListOldDepartamento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Departamento " + departamentoListOldDepartamento + " since its dpIduser field is not nullable.");
                }
            }
            for (Cajachica cajachicaListOldCajachica : cajachicaListOld) {
                if (!cajachicaListNew.contains(cajachicaListOldCajachica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cajachica " + cajachicaListOldCajachica + " since its caIduser field is not nullable.");
                }
            }
            for (Proveedor proveedorListOldProveedor : proveedorListOld) {
                if (!proveedorListNew.contains(proveedorListOldProveedor)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Proveedor " + proveedorListOldProveedor + " since its prIduser field is not nullable.");
                }
            }
            for (Dventa dventaListOldDventa : dventaListOld) {
                if (!dventaListNew.contains(dventaListOldDventa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Dventa " + dventaListOldDventa + " since its dveIduser field is not nullable.");
                }
            }
            for (Dcompra dcompraListOldDcompra : dcompraListOld) {
                if (!dcompraListNew.contains(dcompraListOldDcompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Dcompra " + dcompraListOldDcompra + " since its dcoIduser field is not nullable.");
                }
            }
            for (Stock stockListOldStock : stockListOld) {
                if (!stockListNew.contains(stockListOldStock)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Stock " + stockListOldStock + " since its stIduser field is not nullable.");
                }
            }
            for (Inventario inventarioListOldInventario : inventarioListOld) {
                if (!inventarioListNew.contains(inventarioListOldInventario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Inventario " + inventarioListOldInventario + " since its inIduser field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getClId());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            usuario.setClienteList(clienteListNew);
            List<Compra> attachedCompraListNew = new ArrayList<Compra>();
            for (Compra compraListNewCompraToAttach : compraListNew) {
                compraListNewCompraToAttach = em.getReference(compraListNewCompraToAttach.getClass(), compraListNewCompraToAttach.getCoId());
                attachedCompraListNew.add(compraListNewCompraToAttach);
            }
            compraListNew = attachedCompraListNew;
            usuario.setCompraList(compraListNew);
            List<Venta> attachedVentaListNew = new ArrayList<Venta>();
            for (Venta ventaListNewVentaToAttach : ventaListNew) {
                ventaListNewVentaToAttach = em.getReference(ventaListNewVentaToAttach.getClass(), ventaListNewVentaToAttach.getVeId());
                attachedVentaListNew.add(ventaListNewVentaToAttach);
            }
            ventaListNew = attachedVentaListNew;
            usuario.setVentaList(ventaListNew);
            List<Departamento> attachedDepartamentoListNew = new ArrayList<Departamento>();
            for (Departamento departamentoListNewDepartamentoToAttach : departamentoListNew) {
                departamentoListNewDepartamentoToAttach = em.getReference(departamentoListNewDepartamentoToAttach.getClass(), departamentoListNewDepartamentoToAttach.getDpId());
                attachedDepartamentoListNew.add(departamentoListNewDepartamentoToAttach);
            }
            departamentoListNew = attachedDepartamentoListNew;
            usuario.setDepartamentoList(departamentoListNew);
            List<Cajachica> attachedCajachicaListNew = new ArrayList<Cajachica>();
            for (Cajachica cajachicaListNewCajachicaToAttach : cajachicaListNew) {
                cajachicaListNewCajachicaToAttach = em.getReference(cajachicaListNewCajachicaToAttach.getClass(), cajachicaListNewCajachicaToAttach.getCaId());
                attachedCajachicaListNew.add(cajachicaListNewCajachicaToAttach);
            }
            cajachicaListNew = attachedCajachicaListNew;
            usuario.setCajachicaList(cajachicaListNew);
            List<Proveedor> attachedProveedorListNew = new ArrayList<Proveedor>();
            for (Proveedor proveedorListNewProveedorToAttach : proveedorListNew) {
                proveedorListNewProveedorToAttach = em.getReference(proveedorListNewProveedorToAttach.getClass(), proveedorListNewProveedorToAttach.getPrId());
                attachedProveedorListNew.add(proveedorListNewProveedorToAttach);
            }
            proveedorListNew = attachedProveedorListNew;
            usuario.setProveedorList(proveedorListNew);
            List<Dventa> attachedDventaListNew = new ArrayList<Dventa>();
            for (Dventa dventaListNewDventaToAttach : dventaListNew) {
                dventaListNewDventaToAttach = em.getReference(dventaListNewDventaToAttach.getClass(), dventaListNewDventaToAttach.getDveId());
                attachedDventaListNew.add(dventaListNewDventaToAttach);
            }
            dventaListNew = attachedDventaListNew;
            usuario.setDventaList(dventaListNew);
            List<Dcompra> attachedDcompraListNew = new ArrayList<Dcompra>();
            for (Dcompra dcompraListNewDcompraToAttach : dcompraListNew) {
                dcompraListNewDcompraToAttach = em.getReference(dcompraListNewDcompraToAttach.getClass(), dcompraListNewDcompraToAttach.getDcoId());
                attachedDcompraListNew.add(dcompraListNewDcompraToAttach);
            }
            dcompraListNew = attachedDcompraListNew;
            usuario.setDcompraList(dcompraListNew);
            List<Stock> attachedStockListNew = new ArrayList<Stock>();
            for (Stock stockListNewStockToAttach : stockListNew) {
                stockListNewStockToAttach = em.getReference(stockListNewStockToAttach.getClass(), stockListNewStockToAttach.getStIdinventario());
                attachedStockListNew.add(stockListNewStockToAttach);
            }
            stockListNew = attachedStockListNew;
            usuario.setStockList(stockListNew);
            List<Inventario> attachedInventarioListNew = new ArrayList<Inventario>();
            for (Inventario inventarioListNewInventarioToAttach : inventarioListNew) {
                inventarioListNewInventarioToAttach = em.getReference(inventarioListNewInventarioToAttach.getClass(), inventarioListNewInventarioToAttach.getInId());
                attachedInventarioListNew.add(inventarioListNewInventarioToAttach);
            }
            inventarioListNew = attachedInventarioListNew;
            usuario.setInventarioList(inventarioListNew);
            usuario = em.merge(usuario);
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Usuario oldClIduserOfClienteListNewCliente = clienteListNewCliente.getClIduser();
                    clienteListNewCliente.setClIduser(usuario);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldClIduserOfClienteListNewCliente != null && !oldClIduserOfClienteListNewCliente.equals(usuario)) {
                        oldClIduserOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldClIduserOfClienteListNewCliente = em.merge(oldClIduserOfClienteListNewCliente);
                    }
                }
            }
            for (Compra compraListNewCompra : compraListNew) {
                if (!compraListOld.contains(compraListNewCompra)) {
                    Usuario oldCoIduserOfCompraListNewCompra = compraListNewCompra.getCoIduser();
                    compraListNewCompra.setCoIduser(usuario);
                    compraListNewCompra = em.merge(compraListNewCompra);
                    if (oldCoIduserOfCompraListNewCompra != null && !oldCoIduserOfCompraListNewCompra.equals(usuario)) {
                        oldCoIduserOfCompraListNewCompra.getCompraList().remove(compraListNewCompra);
                        oldCoIduserOfCompraListNewCompra = em.merge(oldCoIduserOfCompraListNewCompra);
                    }
                }
            }
            for (Venta ventaListNewVenta : ventaListNew) {
                if (!ventaListOld.contains(ventaListNewVenta)) {
                    Usuario oldVeIduserOfVentaListNewVenta = ventaListNewVenta.getVeIduser();
                    ventaListNewVenta.setVeIduser(usuario);
                    ventaListNewVenta = em.merge(ventaListNewVenta);
                    if (oldVeIduserOfVentaListNewVenta != null && !oldVeIduserOfVentaListNewVenta.equals(usuario)) {
                        oldVeIduserOfVentaListNewVenta.getVentaList().remove(ventaListNewVenta);
                        oldVeIduserOfVentaListNewVenta = em.merge(oldVeIduserOfVentaListNewVenta);
                    }
                }
            }
            for (Departamento departamentoListNewDepartamento : departamentoListNew) {
                if (!departamentoListOld.contains(departamentoListNewDepartamento)) {
                    Usuario oldDpIduserOfDepartamentoListNewDepartamento = departamentoListNewDepartamento.getDpIduser();
                    departamentoListNewDepartamento.setDpIduser(usuario);
                    departamentoListNewDepartamento = em.merge(departamentoListNewDepartamento);
                    if (oldDpIduserOfDepartamentoListNewDepartamento != null && !oldDpIduserOfDepartamentoListNewDepartamento.equals(usuario)) {
                        oldDpIduserOfDepartamentoListNewDepartamento.getDepartamentoList().remove(departamentoListNewDepartamento);
                        oldDpIduserOfDepartamentoListNewDepartamento = em.merge(oldDpIduserOfDepartamentoListNewDepartamento);
                    }
                }
            }
            for (Cajachica cajachicaListNewCajachica : cajachicaListNew) {
                if (!cajachicaListOld.contains(cajachicaListNewCajachica)) {
                    Usuario oldCaIduserOfCajachicaListNewCajachica = cajachicaListNewCajachica.getCaIduser();
                    cajachicaListNewCajachica.setCaIduser(usuario);
                    cajachicaListNewCajachica = em.merge(cajachicaListNewCajachica);
                    if (oldCaIduserOfCajachicaListNewCajachica != null && !oldCaIduserOfCajachicaListNewCajachica.equals(usuario)) {
                        oldCaIduserOfCajachicaListNewCajachica.getCajachicaList().remove(cajachicaListNewCajachica);
                        oldCaIduserOfCajachicaListNewCajachica = em.merge(oldCaIduserOfCajachicaListNewCajachica);
                    }
                }
            }
            for (Proveedor proveedorListNewProveedor : proveedorListNew) {
                if (!proveedorListOld.contains(proveedorListNewProveedor)) {
                    Usuario oldPrIduserOfProveedorListNewProveedor = proveedorListNewProveedor.getPrIduser();
                    proveedorListNewProveedor.setPrIduser(usuario);
                    proveedorListNewProveedor = em.merge(proveedorListNewProveedor);
                    if (oldPrIduserOfProveedorListNewProveedor != null && !oldPrIduserOfProveedorListNewProveedor.equals(usuario)) {
                        oldPrIduserOfProveedorListNewProveedor.getProveedorList().remove(proveedorListNewProveedor);
                        oldPrIduserOfProveedorListNewProveedor = em.merge(oldPrIduserOfProveedorListNewProveedor);
                    }
                }
            }
            for (Dventa dventaListNewDventa : dventaListNew) {
                if (!dventaListOld.contains(dventaListNewDventa)) {
                    Usuario oldDveIduserOfDventaListNewDventa = dventaListNewDventa.getDveIduser();
                    dventaListNewDventa.setDveIduser(usuario);
                    dventaListNewDventa = em.merge(dventaListNewDventa);
                    if (oldDveIduserOfDventaListNewDventa != null && !oldDveIduserOfDventaListNewDventa.equals(usuario)) {
                        oldDveIduserOfDventaListNewDventa.getDventaList().remove(dventaListNewDventa);
                        oldDveIduserOfDventaListNewDventa = em.merge(oldDveIduserOfDventaListNewDventa);
                    }
                }
            }
            for (Dcompra dcompraListNewDcompra : dcompraListNew) {
                if (!dcompraListOld.contains(dcompraListNewDcompra)) {
                    Usuario oldDcoIduserOfDcompraListNewDcompra = dcompraListNewDcompra.getDcoIduser();
                    dcompraListNewDcompra.setDcoIduser(usuario);
                    dcompraListNewDcompra = em.merge(dcompraListNewDcompra);
                    if (oldDcoIduserOfDcompraListNewDcompra != null && !oldDcoIduserOfDcompraListNewDcompra.equals(usuario)) {
                        oldDcoIduserOfDcompraListNewDcompra.getDcompraList().remove(dcompraListNewDcompra);
                        oldDcoIduserOfDcompraListNewDcompra = em.merge(oldDcoIduserOfDcompraListNewDcompra);
                    }
                }
            }
            for (Stock stockListNewStock : stockListNew) {
                if (!stockListOld.contains(stockListNewStock)) {
                    Usuario oldStIduserOfStockListNewStock = stockListNewStock.getStIduser();
                    stockListNewStock.setStIduser(usuario);
                    stockListNewStock = em.merge(stockListNewStock);
                    if (oldStIduserOfStockListNewStock != null && !oldStIduserOfStockListNewStock.equals(usuario)) {
                        oldStIduserOfStockListNewStock.getStockList().remove(stockListNewStock);
                        oldStIduserOfStockListNewStock = em.merge(oldStIduserOfStockListNewStock);
                    }
                }
            }
            for (Inventario inventarioListNewInventario : inventarioListNew) {
                if (!inventarioListOld.contains(inventarioListNewInventario)) {
                    Usuario oldInIduserOfInventarioListNewInventario = inventarioListNewInventario.getInIduser();
                    inventarioListNewInventario.setInIduser(usuario);
                    inventarioListNewInventario = em.merge(inventarioListNewInventario);
                    if (oldInIduserOfInventarioListNewInventario != null && !oldInIduserOfInventarioListNewInventario.equals(usuario)) {
                        oldInIduserOfInventarioListNewInventario.getInventarioList().remove(inventarioListNewInventario);
                        oldInIduserOfInventarioListNewInventario = em.merge(oldInIduserOfInventarioListNewInventario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getUsId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getUsId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cliente> clienteListOrphanCheck = usuario.getClienteList();
            for (Cliente clienteListOrphanCheckCliente : clienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Cliente " + clienteListOrphanCheckCliente + " in its clienteList field has a non-nullable clIduser field.");
            }
            List<Compra> compraListOrphanCheck = usuario.getCompraList();
            for (Compra compraListOrphanCheckCompra : compraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Compra " + compraListOrphanCheckCompra + " in its compraList field has a non-nullable coIduser field.");
            }
            List<Venta> ventaListOrphanCheck = usuario.getVentaList();
            for (Venta ventaListOrphanCheckVenta : ventaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Venta " + ventaListOrphanCheckVenta + " in its ventaList field has a non-nullable veIduser field.");
            }
            List<Departamento> departamentoListOrphanCheck = usuario.getDepartamentoList();
            for (Departamento departamentoListOrphanCheckDepartamento : departamentoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Departamento " + departamentoListOrphanCheckDepartamento + " in its departamentoList field has a non-nullable dpIduser field.");
            }
            List<Cajachica> cajachicaListOrphanCheck = usuario.getCajachicaList();
            for (Cajachica cajachicaListOrphanCheckCajachica : cajachicaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Cajachica " + cajachicaListOrphanCheckCajachica + " in its cajachicaList field has a non-nullable caIduser field.");
            }
            List<Proveedor> proveedorListOrphanCheck = usuario.getProveedorList();
            for (Proveedor proveedorListOrphanCheckProveedor : proveedorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Proveedor " + proveedorListOrphanCheckProveedor + " in its proveedorList field has a non-nullable prIduser field.");
            }
            List<Dventa> dventaListOrphanCheck = usuario.getDventaList();
            for (Dventa dventaListOrphanCheckDventa : dventaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Dventa " + dventaListOrphanCheckDventa + " in its dventaList field has a non-nullable dveIduser field.");
            }
            List<Dcompra> dcompraListOrphanCheck = usuario.getDcompraList();
            for (Dcompra dcompraListOrphanCheckDcompra : dcompraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Dcompra " + dcompraListOrphanCheckDcompra + " in its dcompraList field has a non-nullable dcoIduser field.");
            }
            List<Stock> stockListOrphanCheck = usuario.getStockList();
            for (Stock stockListOrphanCheckStock : stockListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Stock " + stockListOrphanCheckStock + " in its stockList field has a non-nullable stIduser field.");
            }
            List<Inventario> inventarioListOrphanCheck = usuario.getInventarioList();
            for (Inventario inventarioListOrphanCheckInventario : inventarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Inventario " + inventarioListOrphanCheckInventario + " in its inventarioList field has a non-nullable inIduser field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
