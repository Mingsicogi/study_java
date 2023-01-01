<template>
  <div>
    <h2 id="page-heading" data-cy="SpidHeading">
      <span v-text="$t('shopApp.spid.home.title')" id="spid-heading">Spids</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('shopApp.spid.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'SpidCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-spid">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('shopApp.spid.home.createLabel')"> Create a new Spid </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && spids && spids.length === 0">
      <span v-text="$t('shopApp.spid.home.notFound')">No spids found</span>
    </div>
    <div class="table-responsive" v-if="spids && spids.length > 0">
      <table class="table table-striped" aria-describedby="spids">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('shopApp.spid.spid')">Spid</span></th>
            <th scope="row"><span v-text="$t('shopApp.spid.email')">Email</span></th>
            <th scope="row"><span v-text="$t('shopApp.spid.pid')">Pid</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="spid in spids" :key="spid.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'SpidView', params: { spidId: spid.id } }">{{ spid.id }}</router-link>
            </td>
            <td>{{ spid.spid }}</td>
            <td>{{ spid.email }}</td>
            <td>
              <div v-if="spid.pid">
                <router-link :to="{ name: 'PidView', params: { pidId: spid.pid.id } }">{{ spid.pid.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'SpidView', params: { spidId: spid.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'SpidEdit', params: { spidId: spid.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(spid)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="shopApp.spid.delete.question" data-cy="spidDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-spid-heading" v-text="$t('shopApp.spid.delete.question', { id: removeId })">
          Are you sure you want to delete this Spid?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-spid"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeSpid()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./spid.component.ts"></script>
