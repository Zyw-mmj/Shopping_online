<template>
  <div>
    <el-table :data="coupons" border stripe>
      <el-table-column prop="couponName" label="Coupon Name"></el-table-column>
      <el-table-column prop="couponDesc" label="Description"></el-table-column>
      <el-table-column prop="discount" label="Discount (%)"></el-table-column>
      <el-table-column prop="couponStartTime" label="Start Time"></el-table-column>
      <el-table-column prop="couponEndTime" label="End Time"></el-table-column>
      <el-table-column label="Actions">
        <template slot-scope="scope">
          <el-button type="primary" @click="editCoupon(scope.row)">Edit</el-button>
          <el-button type="danger" @click="deleteCoupon(scope.row.couponId)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :visible.sync="dialogVisible" title="Edit Coupon">
      <el-form :model="editedCoupon" ref="couponForm" label-width="120px">
        <el-form-item label="Coupon Name">
          <el-input v-model="editedCoupon.couponName"></el-input>
        </el-form-item>
        <el-form-item label="Description">
          <el-input v-model="editedCoupon.couponDesc"></el-input>
        </el-form-item>
        <el-form-item label="Discount (%)">
          <el-input-number v-model="editedCoupon.discount"></el-input-number>
        </el-form-item>
        <el-form-item label="Start Time">
          <el-date-picker v-model="editedCoupon.couponStartTime" type="datetime"></el-date-picker>
        </el-form-item>
        <el-form-item label="End Time">
          <el-date-picker v-model="editedCoupon.couponEndTime" type="datetime"></el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="saveCoupon">Save</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      coupons: [],
      dialogVisible: false,
      editedCoupon: {
        couponId: '',
        couponName: '',
        couponDesc: '',
        discount: '',
        couponStartTime: '',
        couponEndTime: '',
      },
    };
  },
  created() {
    this.fetchCoupons();
  },
  methods: {
    fetchCoupons() {
      axios
        .get('http://localhost:8080/Coupon')
        .then((response) => {
          this.coupons = response.data;
        })
        .catch((error) => {
          console.error('Error fetching coupons:', error);
        });
    },
    editCoupon(coupon) {
      this.editedCoupon = { ...coupon };
      this.dialogVisible = true;
    },
    saveCoupon() {
      const apiUrl = `http://localhost:8080/Coupon/${this.editedCoupon.couponId}`;
      axios
        .put(apiUrl, this.editedCoupon)
        .then((response) => {
          // Update the local data with the updated coupon
          const index = this.coupons.findIndex((coupon) => coupon.couponId === response.data.couponId);
          if (index !== -1) {
            this.coupons.splice(index, 1, response.data);
          } else {
            this.coupons.push(response.data);
          }
          this.dialogVisible = false;
        })
        .catch((error) => {
          console.error('Error saving coupon:', error);
        });
    },
    deleteCoupon(couponId) {
      const apiUrl = `http://localhost:8080/Coupon/${couponId}`;
      axios
        .delete(apiUrl)
        .then(() => {
          // Remove the coupon from the local data
          const index = this.coupons.findIndex((coupon) => coupon.couponId === couponId);
          if (index !== -1) {
            this.coupons.splice(index, 1);
          }
        })
        .catch((error) => {
          console.error('Error deleting coupon:', error);
        });
    },
  },
};
</script>
